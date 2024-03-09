package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SetCategoryCommand implements Command {
    private final UserService userService;
    private final CardService cardService;

    @Autowired
    public SetCategoryCommand(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @Override
    public String command() {
        return "/category";
    }

    @Override
    public String description() {
        return "set category to card";
    }

    @Override
    public String commandReg() {
        return "^\\/category\\s.+";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        Long chatId = message.chat().id();
        String text = message.text();

        User user = userService.findByTelegramId(chatId.toString()).get();
        Integer selectedCardId = user.getSelectedCardId();
        if (selectedCardId == null)
            return Collections.singletonList(new SendMessage(chatId, "\uD83E\uDEE3 Для начала выберите карту"));

        String category = text.split(command())[1].strip();
        Card card = cardService.findById(selectedCardId).get();

        cardService.setCategory(card, category);

        return Collections.singletonList(new SendMessage(chatId, "Успех!"));

    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
