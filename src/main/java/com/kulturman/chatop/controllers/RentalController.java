package com.kulturman.chatop.controllers;

import com.kulturman.chatop.dtos.responses.GenericMessageResponse;
import com.kulturman.chatop.dtos.responses.RentalResponse;
import com.kulturman.chatop.entities.Rental;
import com.kulturman.chatop.entities.User;
import com.kulturman.chatop.exceptions.BadRequestException;
import com.kulturman.chatop.exceptions.NotFoundException;
import com.kulturman.chatop.mappers.RentalMapper;
import com.kulturman.chatop.services.FilesStorageService;
import com.kulturman.chatop.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final FilesStorageService filesStorageService;
    private final RentalMapper rentalMapper;
    public RentalController(RentalService rentalService, FilesStorageService filesStorageService, RentalMapper rentalMapper) {
        this.rentalService = rentalService;
        this.filesStorageService = filesStorageService;
        this.rentalMapper = rentalMapper;
    }

    @PostMapping
    public ResponseEntity<GenericMessageResponse> create(
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

        return ResponseEntity.ok(new GenericMessageResponse("Rental created"));
    }

    @PutMapping("/{rentalId}")
    public ResponseEntity<GenericMessageResponse> update(
        @RequestParam("name") String name,
        @RequestParam("surface") double surface ,
        @RequestParam("price") double price,
        @RequestParam("description") String description,
        @PathVariable long rentalId
    ) throws IOException {
        var rental = rentalService.findById(rentalId);

        if (rental.isEmpty()) {
            throw new NotFoundException("Rental not found");
        }

        Rental rentalUpdated = rental.get();
        rentalUpdated.setName(name);
        rentalUpdated.setSurface(surface);
        rentalUpdated.setPrice(price);
        rentalUpdated.setDescription(description);
        rentalService.save(rentalUpdated);
        return ResponseEntity.ok(new GenericMessageResponse("Rental updated"));
    }

    @GetMapping("")
    public ResponseEntity<List<RentalResponse>> getAll() {
        var owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var rentals = rentalService.findByOwnerId(owner.getId());
        return ResponseEntity.ok(rentalMapper.mapListToResponse(rentals));
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalResponse> getRental(@PathVariable long rentalId) {
        var rental = rentalService.findById(rentalId);

        if (rental.isEmpty()) {
            throw new NotFoundException("Rental not found");
        }

        return ResponseEntity.ok(rentalMapper.mapToResponse(rental.get()));
    }
}
