package ru.andrey.ServerAdd.executables.callbacks;


import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.commands.SetCategoryCommand;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.Collections;
import java.util.List;

@Component
public class SetCategoryCallback implements CallBack {
    private final UserService userService;

    @Autowired
    public SetCategoryCallback(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/category";
    }

    @Override
    public String description() {
        return "set category to card";
    }

    @Override
    public List<SendMessage> handle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.from().id();
        int cardId = Integer.parseInt(callbackQuery.data().split(" ")[1]);

        User user = userService.findByTelegramId(chatId.toString()).get();
        userService.setSelectedCardId(user, cardId);

        String sendText = "Введите /category название категории";
        return Collections.singletonList(new SendMessage(chatId, sendText));
    }

    @Override
    public Boolean supports(String nameCallBack) {
        return nameCallBack.contains(command());
    }
}
