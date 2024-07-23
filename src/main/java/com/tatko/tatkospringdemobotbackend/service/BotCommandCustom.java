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

    private Action action;
    private String messageText;
    private String description;
    private Consumer<Update> consumer;


}
