package com.mastery.java.task.rest;


import com.mastery.java.task.security.AuthRequest;
import com.mastery.java.task.security.AuthResponse;
import com.mastery.java.task.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "The name or the password are incorrect";

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(
            final AuthenticationManager authenticationManager,
            final JWTUtil jwtTokenUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody final AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS_ERROR_MESSAGE, ex);
        }

        final String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

}
