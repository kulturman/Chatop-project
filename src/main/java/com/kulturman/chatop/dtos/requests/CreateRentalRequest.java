package com.kulturman.chatop.dtos.requests;

import lombok.Data;

@Data
public class CreateRentalRequest {
    private String name;

    private double surface;

    private double price;

    private String description;

    private String picture;
}
