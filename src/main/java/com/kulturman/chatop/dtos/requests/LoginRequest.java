package com.kulturman.chatop.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class LoginRequest {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
