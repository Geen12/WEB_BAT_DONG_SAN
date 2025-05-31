package com.example.Intern.Config;

import com.example.Intern.Handle.CustomAuthenticationSuccessHandler;
import com.example.Intern.Utility.Enum.ROLE;  // Import enum ROLE
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // Cấu hình SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole(ROLE.ADMIN.name()) // Chỉ admin truy cập
                        .requestMatchers("/user/**", "/properties/add").hasAnyRole(ROLE.USER.name(), ROLE.ADMIN.name()) // USER và ADMIN có thể truy cập
                        .requestMatchers("/verifications/**").hasAnyRole(ROLE.VERIFICATION.name(), ROLE.ADMIN.name()) // TRANSACTION role
                        .requestMatchers("/legal/**").hasAnyRole(ROLE.LEGAL.name(), ROLE.ADMIN.name()) // LEGAL role
                        .requestMatchers("/properties/addToLegal/**").hasAnyRole(ROLE.USER.name(), ROLE.ADMIN.name())
                        .anyRequest().permitAll() // Các endpoint còn lại không yêu cầu xác thực
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Trang đăng nhập tùy chỉnh
                        .loginProcessingUrl("/login") // Địa chỉ xử lý form login
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true") // Chuyển hướng khi đăng nhập thất bại
                        .permitAll()) // Cho phép tất cả truy cập trang login
                .logout(logout -> logout
                        .logoutUrl("/logout") // Địa chỉ URL logout
                        .logoutSuccessUrl("/login") // Trang chuyển hướng sau khi logout thành công
                        .invalidateHttpSession(true) // Vô hiệu hóa session sau khi logout
                        .clearAuthentication(true) // Xoá thông tin xác thực
                        .addLogoutHandler(new SecurityContextLogoutHandler()) // Xử lý logout
                        .permitAll());
        return http.build();
    }

    // Cấu hình PasswordEncoder để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu bằng BCrypt
    }

    // Cấu hình AuthenticationManager để sử dụng JdbcUserDetailsManager (từ cơ sở dữ liệu)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Sử dụng JdbcUserDetailsManager để xác thực người dùng từ cơ sở dữ liệu
        authenticationManagerBuilder
                .userDetailsService(userDetailsService(null)) // Sử dụng UserDetailsService đã cấu hình
                .passwordEncoder(passwordEncoder()); // Mã hóa mật khẩu

        return authenticationManagerBuilder.build(); // Trả về AuthenticationManager đã cấu hình
    }

    // Cấu hình UserDetailsService để sử dụng JdbcUserDetailsManager
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Cập nhật câu truy vấn SQL để lấy thông tin người dùng từ bảng `users`
        manager.setUsersByUsernameQuery("SELECT user_name, password, status FROM users WHERE user_name = ?");

        // Cập nhật câu truy vấn SQL cho quyền người dùng từ bảng `users`
        manager.setAuthoritiesByUsernameQuery(
                "SELECT user_name, CASE role " +
                        "WHEN 0 THEN 'ROLE_ADMIN' " +
                        "WHEN 1 THEN 'ROLE_USER' " +
                        "WHEN 2 THEN 'ROLE_TRANSACTION' " +
                        "WHEN 3 THEN 'ROLE_LEGAL' END AS role " +
                        "FROM users WHERE user_name = ?");

        return manager; // Trả về JdbcUserDetailsManager đã cấu hình
    }
}