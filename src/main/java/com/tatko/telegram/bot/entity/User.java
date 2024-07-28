package com.tatko.telegram.bot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * User entity.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class User {

    /**
     * ID.
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq",
            initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Telegram Chat ID.
     */
    @Column(name = "CHAT_ID", nullable = false)
    private Long chatId;

    /**
     * First Name.
     */
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    /**
     * Last Name.
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    /**
     * User Name.
     */
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    /**
     * Datetime of registration.
     */
    @Column(name = "REGISTERED_AT", nullable = false)
    private LocalDateTime registeredAt;

}
