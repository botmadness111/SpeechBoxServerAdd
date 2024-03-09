package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.commands.Command;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepeatCallBack implements CallBack {
    private final List<Command> commands;

    @Autowired
    public RepeatCallBack(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/repeat";
    }

    @Override
    public String description() {
        return "repeat the command";
    }

    @Override
    public List<SendMessage> handle(CallbackQuery callbackQuery) {
        String commandName = callbackQuery.data().split(" ")[1];

        Command command = commands.stream().filter(com -> com.supports(commandName)).findFirst().orElse(null);
        Message message = callbackQuery.message();

        if (command != null) {
            return command.handle(message);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean supports(String nameCallBack) {
        return nameCallBack.contains(command());
    }
}
