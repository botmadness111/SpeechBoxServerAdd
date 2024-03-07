package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.utils.OriginalAndTranslation;
import ru.andrey.ServerAdd.services.databases.CardService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public List<SendMessage> handle(CallbackQuery callbackQuery) {
        //delete from db;
        String text = callbackQuery.data().replace(command(), "");

        Message message = callbackQuery.message();

        Long chatId = message.chat().id();

        Map<String, String> map = originalAndTranslation.getOriginalAndTranslate(text);

        String original = map.get(originalAndTranslation.getKeyOriginal());
        String translation = map.get(originalAndTranslation.getKeyTranslation());

        boolean isDeleted = cardService.findByOriginalAndTranslation(original, translation).isPresent();
        cardService.delete(original, translation);


        String responseText = "card ";

        if (isDeleted) responseText += "<" + original + " : " + translation + "> is deleted";
        else responseText += "is deleted already";

        return Collections.singletonList(new SendMessage(chatId, responseText));
    }

    @Override
    public Boolean supports(CallbackQuery callbackQuery) {
        if (callbackQuery == null || callbackQuery.data() == null) return false;
        return callbackQuery.data().contains(command());
    }
}
