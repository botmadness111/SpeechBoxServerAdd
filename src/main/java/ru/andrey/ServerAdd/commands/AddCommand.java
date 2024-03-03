package ru.andrey.ServerAdd.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.regex.Pattern;

@Component
public class AddCommand implements Command {
    private final CardService cardService;
    private final UserService userService;

    public AddCommand(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
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
    public SendMessage handle(Update update) {

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String original = text.split(":")[0].split(command() + " ")[1];
        String translation = text.split(":")[1];


        User user = userService.findByTelegramId(chatId.toString()).orElse(null);
        if (user == null) user = userService.save(new User(chatId.toString(), update.message().chat().username()));

        Card card = new Card(original, translation, user);

        cardService.save(card);

        SendMessage sendMessage = new SendMessage(chatId, description());

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("❌удалить").callbackData("/delete"),

                });

        sendMessage.replyMarkup(inlineKeyboard);

        return sendMessage;
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Pattern.matches(commandReg(), update.message().text());
    }
}
