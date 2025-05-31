package com.example.Intern.Model;

import com.example.Intern.Utility.CustomValidation.PassWord.PassWordValidation;
import com.example.Intern.Utility.CustomValidation.UserName.UserNameValidation;
import com.example.Intern.Utility.Enum.GENDER;
import com.example.Intern.Utility.Enum.ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    @UserNameValidation(message = "Username must not be valid")
    private String userName;

    @PassWordValidation(message = "Password must not be valid")
    private String password;

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phoneNumber;

    @NotBlank(message = "Address must not be blank")
    private String address;

    private LocalDate dateOfBirth;

    private GENDER gender;

    private ROLE role;

    private Boolean acceptTerms;
}
