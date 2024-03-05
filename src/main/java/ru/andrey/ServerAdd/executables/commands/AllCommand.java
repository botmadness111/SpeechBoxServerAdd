package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.services.databases.CardService;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class AllCommand implements Command {
    private final CardService cardService;

    public AllCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String command() {
        return "/all";
    }

    @Override
    public String commandReg() {
        return "/all";
    }

    @Override
    public String description() {
        return "send all cards";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        List<Card> cards = cardService.findAll();

        StringBuilder text = new StringBuilder();
        cards.forEach((card) -> text.append(card.getOriginal()).append(" ").append(card.getTranslation()).append("\n"));

        return new SendMessage(chatId, text.toString());
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Pattern.matches(commandReg(), update.message().text());
    }
}
