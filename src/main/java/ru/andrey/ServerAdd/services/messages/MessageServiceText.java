package ru.andrey.ServerAdd.services.messages;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class MessageServiceText extends MessageService {
    private final UserService userService;
    private final CardService cardService;

    @Autowired
    public MessageServiceText(TelegramBot bot, UserService userService, CardService cardService) {
        super(bot);
        this.userService = userService;
        this.cardService = cardService;
    }

    @Override
    public boolean sendMessage(Update update) {
        Long chat_id = update.message().chat().id();
        String text = update.message().text();
        String username = update.message().chat().username();

        SendMessage sendMessage;

        if (text.equals("add card")) {
            String responseText = "sends : <rus text : eng text>";
            sendMessage = new SendMessage(chat_id, responseText);
        } else if (Pattern.matches(".*:.*", text)) {
            //добавим в бд

            Optional<User> userOptional = userService.findByTelegramId(chat_id.toString());

            User user = userOptional.orElseGet(() -> userService.save(new User(chat_id.toString(), username)));

            String[] input = text.split(":");
            String original = input[0];
            String translation = input[1];
            Card card = new Card(original, translation, user);
            card.setUser(user);

            cardService.save(card);

            String responseText = "Хорошо.";
            sendMessage = new SendMessage(chat_id, responseText);
        } else {
            sendMessage = new SendMessage(chat_id, text);

//            Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
//                    new String[]{"add card", "first row button2"},
//                    new String[]{"second row button1", "second row button2"})
//                    .oneTimeKeyboard(true)   // optional
//                    .resizeKeyboard(true)    // optional
//                    .selective(true);        // optional
//
//            sendMessage.replyMarkup(replyKeyboardMarkup);
        }


        bot.execute(sendMessage);

        return false;
    }
}
