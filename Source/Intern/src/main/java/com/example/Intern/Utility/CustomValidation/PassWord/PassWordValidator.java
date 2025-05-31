package com.example.Intern.Utility.CustomValidation.PassWord;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PassWordValidator implements ConstraintValidator<PassWordValidation, String> {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void initialize(PassWordValidation constraintAnnotation) {}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && pattern.matcher(password).matches();
    }
}
