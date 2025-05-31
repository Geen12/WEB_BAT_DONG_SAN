package com.example.Intern.Controller;

import com.example.Intern.Entity.User;
import com.example.Intern.Handle.UserHandle;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/change-password")
public class ChangePasswordController {

    @Autowired
    private UserHandle userHandle;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị trang đổi mật khẩu
    @GetMapping({"", "/"})
    public String showChangePasswordForm(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) {
            return "redirect:/login";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }
        return "change-password";
    }

    // Xử lý việc thay đổi mật khẩu
    @PostMapping("")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request, Model model) {
        String username = request.getUserPrincipal().getName(); // Get the username of the current user
        User user = userHandle.findUserByUserName(username); // Retrieve user from the database

        // Check if the current password matches the stored password
        if (user != null && userHandle.checkPassword(user, currentPassword)) {
            // If password matches, update the user's password
            user.setPassword(passwordEncoder.encode(newPassword)); // Set the new password
            userHandle.save(user); // Save the updated user

            model.addAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");
            return "change-password"; // Return to the change-password page with success message
        } else {
            model.addAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
            return "change-password"; // Return to the change-password page with error message
        }
    }
}
