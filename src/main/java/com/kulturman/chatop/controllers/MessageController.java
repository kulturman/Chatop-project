package com.kulturman.chatop.controllers;

import com.kulturman.chatop.dtos.requests.CreateMessageRequest;
import com.kulturman.chatop.dtos.responses.GenericMessageResponse;
import com.kulturman.chatop.entities.Message;
import com.kulturman.chatop.entities.User;
import com.kulturman.chatop.exceptions.NotFoundException;
import com.kulturman.chatop.services.MessageService;
import com.kulturman.chatop.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/messages")
public class MessageController {
    private final RentalService rentalService;
    private final MessageService messageService;

    public MessageController(RentalService rentalService, MessageService messageService) {
        this.rentalService = rentalService;
        this.messageService = messageService;
    }

    @PostMapping("")
    public ResponseEntity<GenericMessageResponse> create(@RequestBody CreateMessageRequest createMessageRequest) {
        var owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var rental = rentalService.findById(createMessageRequest.getRentalId());

        if (rental.isEmpty()) {
            throw new NotFoundException("Rental not found");
        }
        var message = new Message(createMessageRequest.getMessage(), owner, rental.get());
        messageService.save(message);
        return ResponseEntity.ok(new GenericMessageResponse("Message created"));
    }
}
