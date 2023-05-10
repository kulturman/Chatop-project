package com.kulturman.chatop.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RentalResponse {
    public Long id;
    
    public String name;

    public double surface;

    public double price;

    public String description;

    @JsonProperty("picture")
    public List<String> pictures = new ArrayList<>();

    @JsonProperty("owner_id")
    public long ownerId;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("updated_at")
    public String updatedAt;
}
