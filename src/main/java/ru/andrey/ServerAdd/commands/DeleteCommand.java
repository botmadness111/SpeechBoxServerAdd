package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DeleteCommand implements Command{
    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return "delete card";
    }

    @Override
    public SendMessage handle(Update update) {
        return null;
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Objects.equals((update.message().text()), command());
    }
}
