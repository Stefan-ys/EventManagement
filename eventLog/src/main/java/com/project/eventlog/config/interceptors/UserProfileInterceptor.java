package com.project.eventlog.config.interceptors;

import com.project.eventlog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserProfileInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public UserProfileInterceptor(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String userId = extractUserId(requestURI);

        if (userId == null) {
            throw new IllegalArgumentException("User ID not found in URL path.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("You must be authenticated to access this resource.");
        }

        boolean hasRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Long userIdLong = Long.parseLong(userId);
        boolean isCurrentUser = authentication.getName().equals(userService.getUserById(userIdLong).getUsername());
        if (!isCurrentUser && !hasRoleAdmin) {
            throw new Exception("You do not have permission to access this resource.");
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            Logger logger = LoggerFactory.getLogger(UserProfileInterceptor.class);
            logger.error("Error in UserProfileInterceptor: {}", ex.getMessage());
        }
    }

    private String extractUserId(String requestURI) {
        Pattern pattern = Pattern.compile("^/users/(\\d+)/.*$");
        Matcher matcher = pattern.matcher(requestURI);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }
}
