package com.example.Intern.Utility.CustomValidation.UserName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserNameValidation, String> {

    @Override
    public void initialize(UserNameValidation constraintAnnotation) {
        // Khởi tạo nếu cần
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Logic xác thực
        if (value == null || value.isEmpty()) {
            return false; // Không hợp lệ
        }
        // Ví dụ: Chỉ cho phép chuỗi có độ dài lớn hơn 5
        return value.length() > 5;
    }
}