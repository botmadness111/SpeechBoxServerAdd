package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.andrey.ServerAdd.exceptions.CardErrorException;
import ru.andrey.ServerAdd.exceptions.CardNotAddException;
import ru.andrey.ServerAdd.executables.utils.DeleteInlineKeyboardMarkup;
import ru.andrey.ServerAdd.executables.utils.MyDataBinder;
import ru.andrey.ServerAdd.executables.utils.OriginalAndTranslation;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;
import ru.andrey.ServerAdd.validation.CardValidator;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class AddCommand implements Command {
    private final CardService cardService;
    private final UserService userService;
    private final OriginalAndTranslation originalAndTranslation;
    private final CardValidator cardValidator;
    private final DeleteInlineKeyboardMarkup deleteInlineKeyboardMarkup;

    public AddCommand(CardService cardService, UserService userService, OriginalAndTranslation originalAndTranslation, CardValidator cardValidator, DeleteInlineKeyboardMarkup deleteInlineKeyboardMarkup) {
        this.cardService = cardService;
        this.userService = userService;
        this.originalAndTranslation = originalAndTranslation;
        this.cardValidator = cardValidator;
        this.deleteInlineKeyboardMarkup = deleteInlineKeyboardMarkup;
    }

    @Override
    public String command() {
        return "/add";
    }

    @Override
    public String commandReg() {
        return "^/add\\s.*?:.*$";
    }

    @Override
    public String description() {
        return "sends : <rus text : eng text>";
    }

    @Override
    public List<SendMessage> handle(Update update) {

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        Map<String, String> map = originalAndTranslation.getOriginalAndTranslate(text);

        String original = map.get(originalAndTranslation.getKeyOriginal());
        String translation = map.get(originalAndTranslation.getKeyTranslation());


        User user = userService.findByTelegramId(chatId.toString()).orElse(null);
        if (user == null) user = userService.save(new User(chatId.toString(), update.message().chat().username()));

        Card card = new Card(original, translation, user);

        MyDataBinder myDataBinder = new MyDataBinder(card, cardValidator);
        Optional<String> optionalErrors = myDataBinder.findErrors();
        if (optionalErrors.isPresent()) {
            throw new CardNotAddException(chatId, optionalErrors.get());
        }


        cardService.save(card);

        String textToSend = "✅Карточка добавлена" + "\n\n"
                + "Оригинал: " + original + "\n"
                + "Перевод: " + translation + "\n"
                + "Категория: " + card.getNameCategory();

        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        InlineKeyboardMarkup inlineKeyboard = deleteInlineKeyboardMarkup.getDelete(original, translation);

        sendMessage.replyMarkup(inlineKeyboard);

        return Collections.singletonList(sendMessage);
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Pattern.matches(commandReg(), update.message().text());
    }

}
