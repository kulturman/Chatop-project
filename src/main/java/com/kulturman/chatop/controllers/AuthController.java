package com.kulturman.chatop.controllers;

import com.kulturman.chatop.dtos.requests.LoginRequest;
import com.kulturman.chatop.dtos.requests.RegisterRequest;
import com.kulturman.chatop.dtos.responses.LoginResponse;
import com.kulturman.chatop.dtos.responses.MeResponse;
import com.kulturman.chatop.entities.User;
import com.kulturman.chatop.exceptions.BadRequestException;
import com.kulturman.chatop.services.UserService;
import com.kulturman.chatop.services.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(authentication)));
    }

    @GetMapping("me")
    public ResponseEntity<MeResponse> me() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new MeResponse(user.getEmail(), user.getName()));
    }
}
