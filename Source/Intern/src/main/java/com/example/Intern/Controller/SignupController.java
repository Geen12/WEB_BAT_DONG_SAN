package com.example.Intern.Controller;

import com.example.Intern.Entity.User;
import com.example.Intern.Handle.SignUpHandle;
import com.example.Intern.Handle.UserHandle;
import com.example.Intern.Model.UserDTO;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_USER;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignUpHandle signUpHandle;
    @GetMapping("")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/process")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (signUpHandle.signUp(userDTO) != null) {
            signUpHandle.sendAcpEmail(userDTO);
            return ResponseEntity.ok("Gửi email xác thực thành công");
        }
        return ResponseEntity.badRequest().body("Thông tin đã tồn tại. Vui lòng kiểm tra lại Username hoặc email");
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("Token") String token) {
        try {
            return ResponseEntity.ok(signUpHandle.checkAcpMail(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}