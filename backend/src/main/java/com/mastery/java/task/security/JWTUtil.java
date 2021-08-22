package com.mastery.java.task.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTUtil {

    private final String secretKey;
    private final long sessionTime;

    @Autowired
    public JWTUtil(
            @Value("${jwt.secret}") final String secretKey,
            @Value("${jwt.sessionTime}") final long sessionTime
    ) {
        this.secretKey = secretKey;
        this.sessionTime = sessionTime;
    }

    //  Generation of token
    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        final String commaSeparatedListOfAuthorities =
                userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

        claims.put("authorities", commaSeparatedListOfAuthorities);

        return createToken(claims, userDetails.getUsername());
    }

//    Get an username from the token
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    Get an authorities from the token
    public String extractAuthorities(final String token) {

        final Function<Claims, String> claimsListFunction =
                claims -> (String) claims.get("authorities");

        return extractClaim(token, claimsListFunction);
    }

//  apply - convert one type to another
    private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    Get Claims
    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)                                        //Login
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    private Date expirationTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime);
    }

}





























