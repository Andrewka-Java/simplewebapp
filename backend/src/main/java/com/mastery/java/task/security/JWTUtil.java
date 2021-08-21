package com.mastery.java.task.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.sessionTime}")
    private long sessionTime;

//  Generation of token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String commaSeparatedListOfAuthorities =
                userDetails.getAuthorities()
                    .stream()
                    .map(a->a.getAuthority())
                    .collect(Collectors.joining(","));

        claims.put("authorities", commaSeparatedListOfAuthorities);

        return createToken(claims, userDetails.getUsername());
    }

//    Get an username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    Get an authorities from the token
    public String extractAuthorities(String token) {

        Function<Claims, String> claimsListFunction =
                claims -> (String) claims.get("authorities");

        return extractClaim(token, claimsListFunction);
    }

//  apply - convert one type to another
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    Get Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)                                        //Login
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private Date expirationTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime);
    }



}





























