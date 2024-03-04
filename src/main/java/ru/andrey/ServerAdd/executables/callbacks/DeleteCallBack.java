package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.OriginalAndTranslation;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.services.databases.CardService;

import java.util.Map;
import java.util.Objects;

@Component
public class DeleteCallBack implements CallBack {
    private final OriginalAndTranslation originalAndTranslation;

    private final CardService cardService;

    public DeleteCallBack(OriginalAndTranslation originalAndTranslation, CardService cardService) {
        this.originalAndTranslation = originalAndTranslation;
        this.cardService = cardService;
    }

    @Override
    public String command() {
        return "/delete";
    }

    @Override
    public String description() {
        return "delete card";
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        //delete from db;
        String text = callbackQuery.data().replace(command(), "");

        Message message = callbackQuery.message();

        Long chatId = message.chat().id();

        Map<String, String> map = originalAndTranslation.getOriginalAndTranslate(text);

        String original = map.get(originalAndTranslation.getKeyOriginal());
        String translation = map.get(originalAndTranslation.getKeyTranslation());

        Card card = cardService.delete(original, translation);

        String responseText = "card ";

        if (card != null) responseText += card.getOriginal() + " : " + card.getTranslation() + " is deleted";
        else responseText += "is deleted already";

        return new SendMessage(chatId, responseText);
    }

    @Override
    public Boolean supports(CallbackQuery callbackQuery) {
        if (callbackQuery == null || callbackQuery.data() == null) return false;
        return callbackQuery.data().contains(command());
    }
}