package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.exceptions.CardNotAddException;
import ru.andrey.ServerAdd.executables.utils.DeleteInlineKeyboardButton;
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
    private final DeleteInlineKeyboardButton deleteInlineKeyboardMarkup;

    public AddCommand(CardService cardService, UserService userService, OriginalAndTranslation originalAndTranslation, CardValidator cardValidator, DeleteInlineKeyboardButton deleteInlineKeyboardMarkup) {
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
    public List<SendMessage> handle(Message message) {

        Long chatId = message.chat().id();
        String text = message.text();

        Map<String, String> map = originalAndTranslation.getOriginalAndTranslate(text);

        String original = map.get(originalAndTranslation.getKeyOriginal());
        String translation = map.get(originalAndTranslation.getKeyTranslation());


        User user = userService.findByTelegramId(chatId.toString()).orElse(null);
        if (user == null) user = userService.save(new User(chatId.toString(), message.chat().username()));

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

        InlineKeyboardButton inlineKeyboardButton = deleteInlineKeyboardMarkup.getDelete(original, translation);

        sendMessage.replyMarkup(new InlineKeyboardMarkup(inlineKeyboardButton));

        return Collections.singletonList(sendMessage);
    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }

}
