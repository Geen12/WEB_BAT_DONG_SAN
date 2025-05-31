package com.example.Intern.Controller;

import com.example.Intern.Handle.SignUpHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Autowired
    SignUpHandle signUpHandle;
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("Token") String token) {
        try {
            return ResponseEntity.ok(signUpHandle.checkAcpMail(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
