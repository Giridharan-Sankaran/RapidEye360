package com.example.RapidEYE360.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//This java class is to handle customer access denial for unauthorized login.
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    //Method to handle unauthorized access exception.
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
    }
}
