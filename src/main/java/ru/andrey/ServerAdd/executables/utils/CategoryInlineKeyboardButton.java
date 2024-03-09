package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.springframework.stereotype.Component;

@Component
public class CategoryInlineKeyboardButton {
    public InlineKeyboardButton getCategory(int cardId) {
        return new InlineKeyboardButton("\uD83E\uDDE9Добавить категорию").callbackData("/category " + cardId);
    }
}
