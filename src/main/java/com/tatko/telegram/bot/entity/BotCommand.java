package com.tatko.telegram.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User entity.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "BOT_COMMANDS")
public class BotCommand {

    /**
     * ID.
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(name = "NAME", nullable = false)
//    private String name;
//
//    @Column(name = "BOT_MESSAGE_ID", nullable = false)
//    private String botMessageId;
//
//    @Column(name = "BOT_DESCRIPTION", nullable = false)
//    private String botDescription;
//
//    @Column(name = "BEAN_NAME", nullable = false)
//    private String beanName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "CREATING_TIME", nullable = false)
    private LocalDateTime creatingTime;


}
