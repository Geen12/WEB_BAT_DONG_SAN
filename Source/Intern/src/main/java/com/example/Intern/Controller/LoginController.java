package com.example.Intern.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping({"/", ""})
    public String showLoginPage() {
        // Kiểm tra nếu người dùng đã đăng nhập
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            // Người dùng đã đăng nhập, chuyển hướng tới trang index
            return "redirect:/index";
        }
        // Nếu chưa đăng nhập, hiển thị trang login
        return "login";  // Trang login của bạn
    }
}
