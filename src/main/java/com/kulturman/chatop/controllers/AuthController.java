package com.kulturman.chatop.controllers;

import com.kulturman.chatop.dtos.requests.LoginRequest;
import com.kulturman.chatop.dtos.requests.RegisterRequest;
import com.kulturman.chatop.entities.User;
import com.kulturman.chatop.exceptions.BadRequestException;
import com.kulturman.chatop.services.UserService;
import com.kulturman.chatop.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        var existingUser = userService.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new BadRequestException("Email is arlready used");
        }
        var user = new User();
        BeanUtils.copyProperties(registerRequest, user);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest)  {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getPassword()
        ));
        return ResponseEntity.ok(jwtUtils.generateToken(authentication));
    }
}
