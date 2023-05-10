package com.kulturman.chatop.mappers;

import com.kulturman.chatop.dtos.responses.RentalResponse;
import com.kulturman.chatop.entities.Rental;
import com.kulturman.chatop.services.FilesStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {
    private final FilesStorageService filesStorageService;

    public RentalMapper(FilesStorageService filesStorageService) {
        this.filesStorageService = filesStorageService;
    }

    public List<RentalResponse> mapListToResponse(List<Rental> rentals) {
        return rentals.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public RentalResponse mapToResponse(Rental rental) {
        var rentalResponse = new RentalResponse();
        var dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        BeanUtils.copyProperties(rental, rentalResponse);
        rentalResponse.ownerId = rental.getOwner().getId();
        rentalResponse.createdAt = rental.getCreatedAt().format(dateFormatter);
        rentalResponse.updatedAt = rental.getUpdatedAt().format(dateFormatter);
        rentalResponse.pictures.add(filesStorageService.getFileUrl(rental.getPicture()));
        return rentalResponse;
    }
}
