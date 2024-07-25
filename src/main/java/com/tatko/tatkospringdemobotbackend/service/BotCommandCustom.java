package com.tatko.tatkospringdemobotbackend.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotCommandCustom {

    /**
     * Action.
     */
    private Action action;
    /**
     * Message text that referring for this action.
     */
    private String messageText;
    /**
     * Description.
     */
    private String description;
    /**
     * Consumer that process this action.
     */
    private Consumer<Update> consumer;


}
