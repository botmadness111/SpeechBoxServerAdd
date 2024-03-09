package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.exceptions.CardNotAddException;
import ru.andrey.ServerAdd.exceptions.CardNotRegistered;
import ru.andrey.ServerAdd.executables.utils.MyDataBinder;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;
import ru.andrey.ServerAdd.validation.CardValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class SetCategoryCommand implements Command {
    private final UserService userService;
    private final CardService cardService;
    private final CardValidator cardValidator;

    @Autowired
    public SetCategoryCommand(UserService userService, CardService cardService, CardValidator cardValidator) {
        this.userService = userService;
        this.cardService = cardService;
        this.cardValidator = cardValidator;
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

        Card card = cardService.findById(selectedCardId).orElseThrow(() -> new CardNotRegistered(chatId, selectedCardId));
        card.setCategory(category);

        MyDataBinder myDataBinder = new MyDataBinder(card, cardValidator);
        Optional<String> optionalErrors = myDataBinder.findErrors();
        if (optionalErrors.isPresent()){
            throw new CardNotAddException(chatId, optionalErrors.get());
        }

        cardService.setCategory(card, category);


        return Collections.singletonList(new SendMessage(chatId, "\uD83D\uDE42\u200D↔\uFE0F Успех!"));

    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
