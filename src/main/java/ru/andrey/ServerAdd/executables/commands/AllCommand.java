package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.utils.DeleteInlineKeyboardMarkup;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.services.databases.CardService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AllCommand implements Command {
    private final CardService cardService;
    private final DeleteInlineKeyboardMarkup deleteInlineKeyboardMarkup;

    public AllCommand(CardService cardService, DeleteInlineKeyboardMarkup deleteInlineKeyboardMarkup) {
        this.cardService = cardService;
        this.deleteInlineKeyboardMarkup = deleteInlineKeyboardMarkup;
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
    public List<SendMessage> handle(Update update) {
        Long chatId = update.message().chat().id();

        List<Card> cards = cardService.findAll();

        List<SendMessage> listSendMessages = new ArrayList<>();
        for (Card card : cards) {
            InlineKeyboardMarkup inlineKeyboardMarkup = deleteInlineKeyboardMarkup.getDelete(card.getOriginal(), card.getTranslation());

            String text = "\uD83D\uDCDAВаша карточка" + "\n\n"
                    + "Оригинал: " + card.getOriginal() + "\n"
                    + "Перевод: " + card.getTranslation() + "\n"
                    + "Категория: " + card.getNameCategory();

            listSendMessages.add(new SendMessage(chatId, text).replyMarkup(inlineKeyboardMarkup));
        }

        return listSendMessages;
    }

    @Override
    public Boolean supports(Update update) {
        if (update.message() == null) return false;
        return Pattern.matches(commandReg(), update.message().text());
    }
}
