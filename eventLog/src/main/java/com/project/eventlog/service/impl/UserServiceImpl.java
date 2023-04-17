package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.UserChangeUsernameServiceModel;
import com.project.eventlog.domain.dtos.service.UserEditServiceModel;
import com.project.eventlog.domain.dtos.service.UserRegistrationServiceModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.entity.*;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.domain.enums.RoleEnum;
import com.project.eventlog.repository.*;
import com.project.eventlog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final PictureRepository pictureRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           EventRepository eventRepository,
                           CommentRepository commentRepository,
                           PictureRepository pictureRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void registerUser(UserRegistrationServiceModel serviceModel) {
        UserRoleEntity userRole = userRoleRepository.findByRole(RoleEnum.USER);

        UserEntity userEntity = new UserEntity();
        userEntity
                .setUsername(serviceModel.getUsername())
                .setPassword(passwordEncoder.encode(serviceModel.getPassword()))
                .setImageUrl(serviceModel.getImageUrl())
                .setEmail(serviceModel.getEmail())
                .setLocation(serviceModel.getLocation())
                .setFirstName(serviceModel.getFirstName())
                .setLastName(serviceModel.getLastName())
                .setPhoneNumber(serviceModel.getPhoneNumber())
                .getRoles().add(userRole);

        userRepository.save(userEntity);

    }

    @Override
    public List<UserViewModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());

    }

    private String getUserRole(UserEntity userEntity) {
        for (UserRoleEntity userRoleEntity : userEntity.getRoles()) {
            if (userRoleEntity.getRole().equals(RoleEnum.ADMIN)) {
                return "ADMIN";
            }
        }
        return "USER";
    }

    @Override
    public void initializeUsers() {
        if (userRepository.count() == 0) {
            UserRoleEntity adminRole = userRoleRepository.findByRole(RoleEnum.ADMIN);
            UserRoleEntity userRole = userRoleRepository.findByRole(RoleEnum.USER);

            List<UserEntity> users = new ArrayList<>();
            users.add(createUser("admin", "admin", LocationEnum.BURGAS, "Adminin", "Adminov", "admin@abv.bg", "+123456789", adminRole, "https://picsum.photos/id/237/200/300"));
            users.add(createUser("user1", "user1", LocationEnum.GABROVO, "Userin", "Userov", "user1@abv.bg", "+123456789", userRole, ""));
            users.add(createUser("user2", "user2", LocationEnum.DOBRICH, "Userin", "Userov", "user2@abv.bg", "+123456789", userRole, ""));
            users.add(createUser("user3", "user3", LocationEnum.PERNIK, "Userin", "Userov", "user3@abv.bg", "+123456789", userRole, "https://picsum.photos/200/300?grayscale"));
            userRepository.saveAll(users);

        }
    }

    @Override
    public UserViewModel getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        UserViewModel userViewModel = convertToViewModel(userEntity);
        userViewModel.setRole(getUserRole(userEntity));
        return userViewModel;

    }

    @Override
    public void editUser(Long id, UserEditServiceModel userEditServiceModel) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));


        userEntity.setImageUrl(userEditServiceModel.getImageUrl());
        userEntity.setLocation(userEditServiceModel.getLocation());
        userEntity.setFirstName(userEditServiceModel.getFirstName());
        userEntity.setLastName(userEditServiceModel.getLastName());
        userEntity.setEmail(userEditServiceModel.getEmail());
        userEntity.setPhoneNumber(userEditServiceModel.getPhoneNumber());

        userRepository.save(userEntity);

    }

    @Override
    public long fetUserIdByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new IllegalArgumentException("No such user")).getId();
    }

    @Override
    public void changeUsername(Long userId, UserChangeUsernameServiceModel changeUsernameServiceModel) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user with such id"));

        String newUsername = changeUsernameServiceModel.getUsername().trim();
        if (!userEntity.getUsername().equalsIgnoreCase(newUsername) &&
                userRepository.findByUsernameIgnoreCase(newUsername).isPresent()) {
            throw new IllegalArgumentException("User with username " + newUsername + " already exists");
        }

        String oldPassword = changeUsernameServiceModel.getOldPassword().trim();
        String newPassword = changeUsernameServiceModel.getNewPassword().trim();
        String confirmNewPassword = changeUsernameServiceModel.getConfirmNewPassword().trim();

        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new IllegalArgumentException("Wrong old password");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new IllegalArgumentException("New password and confirm new password do not match");
        }

        userEntity.setUsername(newUsername);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
        List<EventsEntity> eventsEntities = eventRepository.findAllByHostId(userId);

        for (EventsEntity event : eventsEntities) {
            event.setHost(userEntity);
            eventRepository.save(event);
        }
        List<CommentEntity> commentEntities = commentRepository.findAllByAuthorId(userId);
        for (CommentEntity comment : commentEntities) {
            comment.setAuthor(userEntity);
            commentRepository.save(comment);
        }

        List<PictureEntity> pictureEntities = pictureRepository.findAllByAuthorId(userId);
        for (PictureEntity picture : pictureEntities) {
            picture.setAuthor(userEntity);
            pictureRepository.save(picture);
        }
    }


    @Override
    public void changeRole(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        for (UserRoleEntity userRole : user.getRoles()) {
            if (userRole.getRole().equals(RoleEnum.ADMIN)) {
                user.getRoles().remove(userRole);
                userRepository.save(user);
                return;
            }
        }
        UserRoleEntity userRole = userRoleRepository.findByRole(RoleEnum.ADMIN);
        user.getRoles().add(userRole);
        userRepository.save(user);
    }


    @Override
    public UserViewModel getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
        return getUserById(userEntity.getId());
    }

    @Override
    public boolean isUserAdmin(String currentUsername) {
        UserEntity userEntity = userRepository
                .findByUsernameIgnoreCase(currentUsername).
                orElseThrow();
        for (UserRoleEntity userRoleEntity : userEntity.getRoles()) {
            if (userRoleEntity.getRole().equals(RoleEnum.ADMIN)) {
                return true;
            }
        }
        return false;
    }


    public UserEntity createUser(String username, String password, LocationEnum location, String firstName, String
            lastName, String
                                         email, String phoneNumber, UserRoleEntity userRole, String imageUrl) {
        UserEntity user = new UserEntity();
        user.setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setLocation(location)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .getRoles().add(userRole);
        if (!imageUrl.isEmpty() || !imageUrl.isBlank()) {
            user.setImageUrl(imageUrl);
        }
        return user;
    }

    private UserViewModel convertToViewModel(UserEntity userEntity) {
        return new UserViewModel()
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setImageUrl(userEntity.getImageUrl())
                .setLocation(userEntity.getLocation().name())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setEmail(userEntity.getEmail())
                .setPhoneNumber(userEntity.getPhoneNumber())
                .setRole(getUserRole(userEntity))
                .setNumberOfEventsHosted(userEntity.getEventsHosted().size())
                .setNumberOfEventsJoined(userEntity.getEventsJoined().size());
    }
}
