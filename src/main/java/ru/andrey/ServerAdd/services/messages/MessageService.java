package ru.andrey.ServerAdd.services.messages;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

public abstract class MessageService {
    public final TelegramBot bot;

    public MessageService(TelegramBot bot) {
        this.bot = bot;
    }

    abstract public boolean sendMessage(Update update);
}
