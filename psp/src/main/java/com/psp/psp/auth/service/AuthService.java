package com.psp.psp.auth.service;

import com.psp.psp.auth.dto.AuthenticationResponse;
import com.psp.psp.auth.dto.LoginCredentials;
import com.psp.psp.model.User;
import com.psp.psp.repository.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IUserRepository iUserRepository;
    private final JwtService jwtService;

    public AuthService(IUserRepository iUserRepository, JwtService jwtService) {
        this.iUserRepository = iUserRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse Login(LoginCredentials credentials){
        User user = iUserRepository.findByEmail(credentials.getEmail());
        if(user == null || !user.validatePassword(credentials.getPassword())) return null; //TODO: Return message indicating wrong authentication

        String token = jwtService.generateJwtToken(user);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwtToken(token);
        return response;
    }
}