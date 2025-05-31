package com.example.Intern.Handle;

import com.example.Intern.Entity.User;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserHandle {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Lấy tất cả người dùng theo trạng thái
    public List<User> findUsersByStatus(STATUS_USER statusUser) {
        return userRepository.findUsersByStatus(statusUser);
    }

    // Cập nhật trạng thái người dùng
    public void updateUserStatusByUserName(String userName, STATUS_USER statusUser) {
        userRepository.updateStatusByUserName(userName, statusUser);
    }

    // Xóa người dùng
    public void deleteUserByUserName(String userName) {
        userRepository.deleteByUserName(userName); // Gọi phương thức xóa người dùng trong UserRepository
    }

    // Tạo người dùng mới
    public User createUser(User user) {
        return userRepository.save(user); // Lưu người dùng mới vào cơ sở dữ liệu
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName); // Assumes the method exists in your repository
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean checkPassword(User user, String currentPassword) {
        return passwordEncoder.matches(currentPassword, user.getPassword()); // Compare the provided password with the stored password
    }
}
