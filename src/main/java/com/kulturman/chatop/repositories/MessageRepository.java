package com.kulturman.chatop.repositories;

import com.kulturman.chatop.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
