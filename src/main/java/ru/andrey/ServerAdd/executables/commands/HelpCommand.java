package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Выводит список всех команд" + "\n" + "Введите /help";
    }

    @Override
    public String commandReg() {
        return "/help";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        Long chatId = message.chat().id();

        StringBuilder sb = new StringBuilder("\uD83D\uDE42\u200D↔\uFE0F Используй эти команды: ");

        for (Command command : commands) {
            sb.append("\n");
            sb.append(command.command()).append(" - ").append(command.description());
            sb.append("\n");
        }

        return Collections.singletonList(new SendMessage(chatId, sb.toString()));
    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
