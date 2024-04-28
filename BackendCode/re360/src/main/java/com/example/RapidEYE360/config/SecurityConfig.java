package com.example.RapidEYE360.config;

import com.example.RapidEYE360.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//This java class handles Security configurations for the login credentials of Store Manager and Clerk.
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsServiceImp; //Declaration of User Details Service
    private final JwtAuthenticationFilter jwtAuthenticationFilter; //Declaration of JWT Authentication Filter

    private final CustomAccessDeniedHandler accessDeniedHandler; //Declaration of access denied handler
    public SecurityConfig(UserDetailsService userDetailsServiceImp,
                          JwtAuthenticationFilter authenticationFilter,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomAccessDeniedHandler accessDeniedHandler) {  //Creation of Constructor with parameters
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        //this.wtAuthenticationFilter = jwtAuthenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/login/**") //Allow permission to access all the URLs if they have valid token
                                .permitAll()
                                .requestMatchers("/register/**").hasAuthority("Manager")
                                .requestMatchers("/supplier/listAll/**","/supplier/create/**","/supplier/brandName/{brandName}/**","/supplier/{brandName}/**").hasAuthority("Manager")
//                                .requestMatchers("/discount/listAll/**","/discount/UPCID/{UPCID}/**","/expiry/listAll/**","/expiry/UPCID/{UPCID}/**","/expiry/expirydate/**","/products/all/**","/products/UPCID/{upcID}/**","/products/Category/{category}/**","/products/Brand/{brand}/**","/expiry/statusUpdate/updateDiscount/**","/expiry/statusUpdate/updateExpiry").hasAnyAuthority("Manager","Clerk")
//                                .requestMatchers("/discount/statusUpdate/updateDiscount/**","/expiry/statusUpdate/updateExpiry/**").hasAuthority("Clerk")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
