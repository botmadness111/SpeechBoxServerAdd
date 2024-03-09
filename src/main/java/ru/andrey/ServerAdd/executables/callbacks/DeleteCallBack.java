package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.exceptions.CardNotRegistered;
import ru.andrey.ServerAdd.executables.utils.OriginalAndTranslation;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DeleteCallBack implements CallBack {
    private final UserService userService;

    private final CardService cardService;

    public DeleteCallBack(UserService userService, CardService cardService) {
        this.userService = userService;
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

        Message message = callbackQuery.message();

        Long chatId = message.chat().id();


        int cardId = Integer.parseInt(callbackQuery.data().split(" ")[1]);
        Card card = cardService.findById(cardId).orElseThrow(() -> new CardNotRegistered(chatId, cardId));

        User user = userService.findByTelegramId(chatId.toString()).get();

        String original = card.getOriginal();
        String translation = card.getTranslation();
        String category = card.getCategory();

        cardService.delete(original, translation, category, user);

        String responseText = "";

        responseText += "\uD83D\uDE0C Карта удалена";

        return Collections.singletonList(new SendMessage(chatId, responseText));
    }

    @Override
    public Boolean supports(String nameCallBack) {
        return nameCallBack.contains(command());
    }
}
