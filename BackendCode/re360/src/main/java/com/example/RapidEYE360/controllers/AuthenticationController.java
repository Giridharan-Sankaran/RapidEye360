package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.AuthenticationResponse;
import com.example.RapidEYE360.models.Clerk;
import com.example.RapidEYE360.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//This controller class is created for handling API requests for login.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Clerk request
            ){
            return ResponseEntity.ok(authService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody Clerk request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<AuthenticationResponse> refresh(
//            @RequestBody Map<String, String> refreshTokenRequest) {
//        // Extract refresh token from the request
//        String refreshToken = refreshTokenRequest.get("refresh_token");
//
//        // Validate and generate a new access token
//        AuthenticationResponse refreshedTokens = authService.refreshToken(refreshToken);
//
//        // Return the refreshed tokens
//        return ResponseEntity.ok(refreshedTokens);
//    }


}
