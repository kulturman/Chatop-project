package com.kulturman.chatop.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    @Size(min = 8)
    private String password;
}