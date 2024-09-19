package com.tatko.telegram.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * User entity.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    /**
     * ID.
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User Role Name.
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * User Role Description.
     */
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    /**
     * User Role Creating timestamp.
     */
    @Column(name = "CREATING_TIME", nullable = false)
    private LocalDateTime creatingTime;

    /**
     * Set for User entities.
     */
    @OneToMany(mappedBy = "userRole", fetch = FetchType.EAGER)
    private Set<User> users;

    /**
     * Set for UserArch entities.
     */
    @OneToMany(mappedBy = "userRole", fetch = FetchType.EAGER)
    private Set<UserArch> usersArch;

}
