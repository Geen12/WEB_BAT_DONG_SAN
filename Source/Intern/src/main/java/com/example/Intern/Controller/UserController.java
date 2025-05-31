package com.example.Intern.Controller;

import com.example.Intern.Entity.User;
import com.example.Intern.Handle.CustomAuthenticationSuccessHandler;
import com.example.Intern.Handle.UserHandle;
import com.example.Intern.Utility.Enum.STATUS_USER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    @Autowired
    private UserHandle userHandle;

    // Hiển thị tất cả người dùng với trạng thái cụ thể
    @GetMapping("/status/{status}")
    public String getUsersByStatus(@PathVariable String status, Model model) {
        // Chuyển đổi chuỗi thành enum STATUS_USER
        STATUS_USER statusUser = STATUS_USER.valueOf(status.toUpperCase());

        List<User> users = userHandle.findUsersByStatus(statusUser);
        model.addAttribute("users", users);
        return "user_list";  // Trang hiển thị danh sách người dùng
    }

    // Cập nhật trạng thái người dùng
    @PutMapping("/status/{userName}")
    public String updateUserStatus(@PathVariable String userName, @RequestBody STATUS_USER statusUser, Model model) {
        userHandle.updateUserStatusByUserName(userName, statusUser);
        return "redirect:/users/status/" + statusUser; // Chuyển hướng sau khi cập nhật
    }
}
