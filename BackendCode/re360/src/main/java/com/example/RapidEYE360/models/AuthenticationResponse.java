package com.example.RapidEYE360.models;

//This model class holds variables for Authentication and Login.
public class AuthenticationResponse {
    private String token;
    private final String username;

    private final String role;

    public AuthenticationResponse(String token, String username, String role) {

        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole(){
        return role;
    }


}
