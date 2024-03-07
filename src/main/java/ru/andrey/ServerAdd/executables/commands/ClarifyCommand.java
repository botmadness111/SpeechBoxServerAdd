package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ClarifyCommand {

    public SendMessage getClarifySendMessage(Long chatId, int remains) {
        String text = "Осталось " + remains + " карточке" + "\n"
                + "\uD83D\uDC40 Загрузить их?" + "\n";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("✅ДА").callbackData("/yes"),
                        new InlineKeyboardButton("❌НЕТ").callbackData("/no"),

                });

        return new SendMessage(chatId, text).replyMarkup(inlineKeyboardMarkup);
    }

}
