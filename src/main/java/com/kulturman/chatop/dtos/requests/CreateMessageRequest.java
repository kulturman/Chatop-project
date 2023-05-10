package com.kulturman.chatop.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateMessageRequest {
    @NotEmpty
    private String message;

    @NotNull
    private Long rentalId;
}
