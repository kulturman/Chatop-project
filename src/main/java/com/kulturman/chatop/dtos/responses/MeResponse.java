package com.kulturman.chatop.dtos.responses;

public class MeResponse {
    public String email;
    public String name;

    public MeResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
