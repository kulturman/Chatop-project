package com.kulturman.chatop.controllers;

import com.kulturman.chatop.entities.Rental;
import com.kulturman.chatop.entities.User;
import com.kulturman.chatop.exceptions.BadRequestException;
import com.kulturman.chatop.services.FilesStorageService;
import com.kulturman.chatop.services.JwtService;
import com.kulturman.chatop.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final FilesStorageService filesStorageService;

    public RentalController(RentalService rentalService, FilesStorageService filesStorageService, JwtService jwtService) {
        this.rentalService = rentalService;
        this.filesStorageService = filesStorageService;
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestParam("picture") MultipartFile picture,
        @RequestParam("name") String name,
        @RequestParam("surface") double surface ,
        @RequestParam("price") double price,
        @RequestParam("description") String description
    ) throws IOException {
        if (picture.getContentType() != null && !picture.getContentType().startsWith("image/")) {
            throw  new BadRequestException("Only images are allowed");
        }
        String fileName = filesStorageService.save(picture);
        var owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var rental = new Rental(name, surface, price, description, fileName, owner);
        rentalService.save(rental);

        return ResponseEntity.ok().build();
    }
}
