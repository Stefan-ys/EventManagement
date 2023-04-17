package com.project.eventlog.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String passwordFieldName;
    private String confirmPasswordFieldName;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.passwordFieldName = "password";
        this.confirmPasswordFieldName = "confirmPassword";
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        String password = (String) beanWrapper.getPropertyValue(passwordFieldName);
        String confirmPassword = (String) beanWrapper.getPropertyValue(confirmPasswordFieldName);
        if (password == null || confirmPassword == null) {
            return true;
        }

        return password.equals(confirmPassword);
    }
}
