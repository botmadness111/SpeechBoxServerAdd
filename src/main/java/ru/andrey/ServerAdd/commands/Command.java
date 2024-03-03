package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.andrey.ServerAdd.services.databases.CardService;

import java.util.Objects;
import java.util.regex.Pattern;


public interface Command {
    String command();

    String commandReg();

    String description();

    SendMessage handle(Update update);

    default Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Pattern.matches(commandReg(), update.message().text());
    }
}
