package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.andrey.ServerAdd.executables.Executable;

import java.util.List;

public interface Command<T extends SendMessage> extends Executable {

    String commandReg();

    List<SendMessage> handle(Message message);

    Boolean supports(String commandName);
}
