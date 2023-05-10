package com.kulturman.chatop.services;

import com.kulturman.chatop.entities.Message;
import com.kulturman.chatop.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}
