package com.example.RapidEYE360.services;

import com.example.RapidEYE360.repositories.ClerkRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//This service class holds functions for login of Manager and Clerk.
@Service
public class ClerkDetailsServiceImp implements UserDetailsService {

    private final ClerkRepository repository;

    public ClerkDetailsServiceImp(ClerkRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

    }
}
