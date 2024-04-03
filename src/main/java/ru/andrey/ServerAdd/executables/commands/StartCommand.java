package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class StartCommand implements Command {
    private final UserService userService;

    @Autowired
    public StartCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "start chatting";
    }

    @Override
    public String commandReg() {
        return "/start";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        Long chatId = message.chat().id();
        String userName = message.chat().username();

        User newUser = new User(chatId.toString(), userName);

        String text = message.chat().firstName() + "," + " мы уже знакомы :)";

        if (userService.findByTelegramId(chatId.toString()).isPresent())
            return Collections.singletonList(new SendMessage(chatId, text));

        userService.save(newUser);

        text = "Привет " + message.chat().firstName() + "!" + "\n" + "Отправь /help, чтобы ознакомиться с командами";

        return Collections.singletonList(new SendMessage(chatId, text));
    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
