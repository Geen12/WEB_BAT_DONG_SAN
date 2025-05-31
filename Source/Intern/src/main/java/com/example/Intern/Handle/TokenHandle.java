package com.example.Intern.Handle;

import com.example.Intern.Entity.Token;
import com.example.Intern.Entity.User;
import com.example.Intern.Repository.TokenRepository;
import com.example.Intern.Service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenHandle {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    TokenService tokenService;
    public Token newToken(User user) {
        try {
            tokenRepository.deleteTokenWithUser(user.getToken().getToken());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Token token = Token.builder().user(user).token(tokenService.generateToken()).build();
        Token saveToken = tokenRepository.save(token);
        user.setToken(token);
        log.info("Token create:" + saveToken);
        return saveToken;
    }

    public Token findUsernameByToken(String token) {
        return tokenRepository.findTokenByToken(token);
    }

    public void deleteTokenByToken(String token) {
        tokenRepository.deleteTokenWithUser(token);
    }
}
