package com.kulturman.chatop.services;

import com.kulturman.chatop.entities.Rental;
import com.kulturman.chatop.repositories.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }
}
