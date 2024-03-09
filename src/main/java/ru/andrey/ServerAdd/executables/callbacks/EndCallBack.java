package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EndCallBack implements CallBack {
    @Override
    public String command() {
        return "/end";
    }

    @Override
    public String description() {
        return "end of the process";
    }

    @Override
    public List<SendMessage> handle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.from().id();

        return Collections.singletonList(new SendMessage(chatId, "Хорошо."));
    }

    @Override
    public Boolean supports(String nameCallBack) {
        return nameCallBack.contains(command());
    }
}
