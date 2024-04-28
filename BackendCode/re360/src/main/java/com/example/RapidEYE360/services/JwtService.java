package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Clerk;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

//This service class holds token details for JWT based authentication.
@Service
public class JwtService {
    private String SECRET_KEY = "8B3ED7993FB2297B03ACC59D1EA4DF42C46B0E9983E546B1196865A51B9E6D1D";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }
    public boolean isValid(String token, UserDetails clerk){
        String username = extractUsername(token);
        return (username.equals(clerk.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(Clerk clerk){
        String token = Jwts
                .builder()
                .subject(clerk.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 6L * 30L * 24L * 60L * 60L * 1000L))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}