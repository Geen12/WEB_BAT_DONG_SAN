package com.example.Intern.Handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();

        // Kiểm tra trạng thái người dùng từ cơ sở dữ liệu
        String status = checkStatusByUsername(username); // Giả sử bạn kiểm tra từ cơ sở dữ liệu

        if ("0".equals(status)) {
            // Nếu trạng thái là unconfirmed, chuyển hướng đến trang inactive
            response.sendRedirect("/inactive");
        } else {
            // Nếu trạng thái hợp lệ, chuyển hướng đến trang home
            response.sendRedirect("/home");
        }
    }

    private String checkStatusByUsername(String username) {
        // Sử dụng JdbcTemplate để chạy câu truy vấn SQL và lấy trạng thái người dùng
        String sql = "SELECT status FROM users WHERE user_name = ?";
        return jdbcTemplate.queryForObject(sql, String.class, username);  // Trả về giá trị của cột 'status'
    }
}