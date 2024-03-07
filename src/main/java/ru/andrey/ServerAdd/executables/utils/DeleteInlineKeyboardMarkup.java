package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class DeleteInlineKeyboardMarkup {
    public InlineKeyboardMarkup getDelete(String original, String translation) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("❌удалить").callbackData("/delete " + original + " : " + translation),

                });
    }
}
