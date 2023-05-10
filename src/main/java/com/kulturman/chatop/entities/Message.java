package com.kulturman.chatop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "messages")
@NoArgsConstructor
public class Message {
    private String message;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "id")
    private Rental rental;

    public Message(String message, User user, Rental rental) {
        this.message = message;
        this.user = user;
        this.rental = rental;
    }
}
