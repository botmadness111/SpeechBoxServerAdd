package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.User;

import java.util.Objects;

@Component
public class DeleteCallBack implements CallBack {
    @Override
    public String command() {
        return "/delete";
    }

    @Override
    public String description() {
        return "delete card";
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        //delete from db;
        return null;
    }

    @Override
    public Boolean supports(CallbackQuery callbackQuery) {
        if (callbackQuery == null || callbackQuery.data() == null) return false;
        return Objects.equals(callbackQuery.data(), command());
    }
}
