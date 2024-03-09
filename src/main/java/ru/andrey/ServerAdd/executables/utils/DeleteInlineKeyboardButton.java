package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.springframework.stereotype.Component;

@Component
public class DeleteInlineKeyboardButton {

    public InlineKeyboardButton getDelete(Integer cardId) {
        return new InlineKeyboardButton("❌Удалить").callbackData("/delete " + cardId);
    }
}
