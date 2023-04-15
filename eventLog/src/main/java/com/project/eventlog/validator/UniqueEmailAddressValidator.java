package com.project.eventlog.validator;

import com.project.eventlog.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailAddressValidator  implements ConstraintValidator<UniqueEmailAddress, String> {

    private final UserRepository userRepository;

    public UniqueEmailAddressValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmailAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.findByEmailIgnoreCase(email).isEmpty();
    }
}
