package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AddCommand implements Command {
    @Override
    public String command() {
        return "/add";
    }

    @Override
    public String description() {
        return "sends : <rus text : eng text>";
    }

    @Override
    public SendMessage handle(Update update) {

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        SendMessage sendMessage = new SendMessage(chatId, description());

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("отмена").callbackData("asd"),

                });

        sendMessage.replyMarkup(inlineKeyboard);

        return sendMessage;
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Objects.equals((update.message().text()), command());
    }
}
