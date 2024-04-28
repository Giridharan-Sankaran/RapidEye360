package com.example.RapidEYE360.filter;


import com.example.RapidEYE360.services.ClerkDetailsServiceImp;
import com.example.RapidEYE360.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//This java class is for JWT based authentication filtering.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; //Declaration of JWT Service
    private final ClerkDetailsServiceImp userDetailsService; //Declaration of Clerk Details Service Implementation

    public JwtAuthenticationFilter(JwtService jwtService, ClerkDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;  //Creation of constructor
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,  //Filtering the request
            @NonNull HttpServletResponse response,  //Filtering the response
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.substring(7);  //Accessing the bearer token
        String username = jwtService.extractUsername(token);  //Accessing the username

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }
        filterChain.doFilter(request, response);

    }
}
