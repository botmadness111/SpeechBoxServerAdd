package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.regex.Pattern;

public interface CallBack {
    String command();

    String description();

    SendMessage handle(CallbackQuery callbackQuery);

     Boolean supports(CallbackQuery callbackQuery);
}
