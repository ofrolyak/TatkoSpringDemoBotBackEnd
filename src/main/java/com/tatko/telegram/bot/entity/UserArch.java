package com.tatko.telegram.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * User entity.
 */

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USERS_ARCH")
public class UserArch {

    /**
     * ID.
     */
    @Id
    @Column(name = "ID", nullable = false)
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

//    /**
//     * User Role Id for this User.
//     */
//    @Column(name = "USER_ROLE_ID", nullable = false)
//    private Long userRoleId;

    /**
     * UserRole entity.
     */
    @ManyToOne
    @JoinColumn(name = "USER_ROLE_ID", nullable = false)
    private UserRole userRole;

}
