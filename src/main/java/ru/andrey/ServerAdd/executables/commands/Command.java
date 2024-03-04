package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.andrey.ServerAdd.executables.Executable;

public interface Command extends Executable {
    String command();

    String commandReg();

    String description();

    SendMessage handle(Update update);

    Boolean supports(Update update);
}
