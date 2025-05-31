package com.example.Intern.Utility.CustomValidation.UserName;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Định nghĩa annotation
@Documented
@Constraint(validatedBy = UserNameValidator.class) // Liên kết với lớp validator
@Target({ ElementType.METHOD, ElementType.FIELD }) // Có thể áp dụng ở đâu
@Retention(RetentionPolicy.RUNTIME) // Thời gian sống
public @interface UserNameValidation {

    String message() default "Invalid value"; // Thông báo lỗi

    Class<?>[] groups() default {}; // Để nhóm validation

    Class<? extends Payload>[] payload() default {}; // Thêm metadata nếu cần
}
