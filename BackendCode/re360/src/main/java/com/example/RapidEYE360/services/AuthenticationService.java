package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.AuthenticationResponse;
import com.example.RapidEYE360.models.Clerk;
import com.example.RapidEYE360.repositories.ClerkRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//This service class holds the functions related to Authentication of Manager and Clerk.
@Service
public class AuthenticationService {
    private final ClerkRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(ClerkRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        //this.tokenRepository = tokenRepository;
        //this.authenticationManager = authenticationManager;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Clerk request) {

        Clerk clerk = new Clerk();
        clerk.setUserName(request.getUsername());
        clerk.setPassWord(passwordEncoder.encode(request.getPassword()));


        clerk.setRole(request.getRole());

        clerk = repository.save(clerk);

        String token = jwtService.generateToken(clerk).toString();

        return new AuthenticationResponse(token, clerk.getUsername(), clerk.getRole());

    }

    public AuthenticationResponse authenticate(Clerk request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Clerk clerk = repository.findByUserName(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(clerk).toString();

        return new AuthenticationResponse(token,clerk.getUsername(), clerk.getRole());

    }

}
