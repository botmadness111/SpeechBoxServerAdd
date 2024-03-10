package ru.andrey.ServerAdd.executables.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.services.databases.CardService;

@Component
public class CategoryInlineKeyboardButton {
    private final CardService cardService;

    public CategoryInlineKeyboardButton(CardService cardService) {
        this.cardService = cardService;
    }

    public InlineKeyboardButton getCategory(int cardId) {

        if (cardService.findById(cardId).get().getCategory() != null)
            return new InlineKeyboardButton("\uD83E\uDDE9 Изменить категорию").callbackData("/category " + cardId);

        return new InlineKeyboardButton("\uD83E\uDDE9 Добавить категорию").callbackData("/category " + cardId);


    }
}
