package com.example.Intern.Handle;

import com.example.Intern.Entity.Token;
import com.example.Intern.Entity.User;
import com.example.Intern.Model.UserDTO;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Service.EmailService;
import com.example.Intern.Utility.Enum.STATUS_USER;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SignUpHandle {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    TokenHandle tokenHandle;


    public User signUp(UserDTO userDTO) {
        User user = User.builder()
                .user_name(userDTO.getUserName())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .full_name(userDTO.getFullName())
                .email(userDTO.getEmail())
                .phone_number(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .date_of_birth(userDTO.getDateOfBirth())
                .gender(userDTO.getGender())
                .accept_terms(userDTO.getAcceptTerms())
                .statusUser(STATUS_USER.UNCONFIRMED)
                .build();
        try {
            User savedUser = userRepository.save(user);
            tokenHandle.newToken(user);
            userRepository.flush();
            log.info("Tài khoản tạo thành công:", savedUser);
            return savedUser;
        } catch (Exception ex) {
            log.info("Thông tin đã tồn tại");
            return null;
        }
    }

    public void sendAcpEmail(UserDTO userDTO) {
        String token = userRepository.findUserByUserName(userDTO.getUserName()).getToken().getToken();
        String text = "http://localhost:8080/verify?Token=" + token;
        emailService.sendEmail(userDTO.getEmail(), "No-reply", text);
    }

    public String checkAcpMail(String token) {
        try {
            Token token1 = tokenHandle.findUsernameByToken(token);
            User user = token1.getUser();
            if ((System.currentTimeMillis() - token1.getCreate_at().getTime()) > 10*60000) {
                String text = "http://localhost:8080/verify?Token=" + tokenHandle.newToken(user).getToken();
                emailService.sendEmail(user.getEmail(), "No-reply", text);
                return "hết hạn, hãy check mail mới";
            } else {
                userRepository.updateStatusByUserName(user.getUser_name(), STATUS_USER.CONFIRMED);
                tokenHandle.deleteTokenByToken(token);
                return "done";
            }
        } catch (Exception ex){
            return "link không hợp lệ";
        }
    }
}

