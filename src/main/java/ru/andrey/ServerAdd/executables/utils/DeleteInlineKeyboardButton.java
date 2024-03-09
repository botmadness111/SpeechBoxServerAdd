package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.springframework.stereotype.Component;

@Component
public class DeleteInlineKeyboardButton {

    public InlineKeyboardButton getDelete(String original, String translation) {
        return new InlineKeyboardButton("❌удалить").callbackData("/delete " + original + " : " + translation);
    }
}
