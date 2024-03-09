package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class YesNoInlineKeyboardMarkup {
    public InlineKeyboardMarkup getYesNo(String commandName) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("✅Yes").callbackData("/repeat " + commandName),
                        new InlineKeyboardButton("❌No").callbackData("/end"),

                });
    }
}
