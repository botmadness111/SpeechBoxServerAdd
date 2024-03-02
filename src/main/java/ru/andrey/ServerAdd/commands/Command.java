package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Objects;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Objects.equals((update.message().text()), command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
