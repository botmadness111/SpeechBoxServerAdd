package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.utils.CategoryInlineKeyboardButton;
import ru.andrey.ServerAdd.executables.utils.DeleteInlineKeyboardButton;
import ru.andrey.ServerAdd.executables.utils.YesNoInlineKeyboardMarkup;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.CardService;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AllCommand implements Command {
    private final CardService cardService;
    private final UserService userService;
    private final DeleteInlineKeyboardButton deleteInlineKeyboardMarkup;
    private final YesNoInlineKeyboardMarkup yesNoInlineKeyboardMarkup;
    private final CategoryInlineKeyboardButton categoryInlineKeyboardMarkup;

    public AllCommand(CardService cardService, UserService userService, DeleteInlineKeyboardButton deleteInlineKeyboardMarkup, YesNoInlineKeyboardMarkup yesNoInlineKeyboardMarkup, CategoryInlineKeyboardButton categoryInlineKeyboardMarkup) {
        this.cardService = cardService;
        this.userService = userService;
        this.deleteInlineKeyboardMarkup = deleteInlineKeyboardMarkup;
        this.yesNoInlineKeyboardMarkup = yesNoInlineKeyboardMarkup;
        this.categoryInlineKeyboardMarkup = categoryInlineKeyboardMarkup;
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
        return "Выводит все ваши карточки" + "\n" + "Введите /all";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        List<SendMessage> listSendMessages = new ArrayList<>();

        Long chatId = message.chat().id();
//        String text = message.text();

        User user = userService.findByTelegramIdWithCards(chatId.toString());
        List<Card> cardsRemains = cardService.findByIdGreaterThan(user.getStopId(), user.getId());

        if (cardsRemains.isEmpty() && user.getStopId() == 0) {
            return Collections.singletonList(new SendMessage(chatId, "\uD83D\uDE44 У вас нет ни одной карточки"));
        }

        if (cardsRemains.isEmpty()) {
            userService.setStopId(user, 0);
            return handle(message);
        }

        for (Card card : cardsRemains) {

            InlineKeyboardButton inlineKeyboardButton1 = deleteInlineKeyboardMarkup.getDelete(card.getId());
            InlineKeyboardButton inlineKeyboardButton2 = categoryInlineKeyboardMarkup.getCategory(card.getId());

            String sendText = "\uD83D\uDCDAВаша карточка" + "\n\n"
                    + "Оригинал: " + card.getOriginal() + "\n"
                    + "Перевод: " + card.getTranslation() + "\n"
                    + "Категория: " + card.getNameCategory();

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{new InlineKeyboardButton[]{inlineKeyboardButton2}, new InlineKeyboardButton[]{inlineKeyboardButton1}});

            listSendMessages.add(new SendMessage(chatId, sendText).replyMarkup(inlineKeyboardMarkup));

        }

        userService.setStopId(user, cardsRemains.get(cardsRemains.size() - 1).getId());

        InlineKeyboardMarkup inlineKeyboardMarkup = yesNoInlineKeyboardMarkup.getYesNo(command());

        int remains = user.getCards().size() - cardService.countCardByIdLessThan(cardsRemains.get(cardsRemains.size() - 1).getId(), user) - 1; // '-1' it is current

        if (remains > 0) {
            String sendText = "\uD83D\uDC40 Осталось еще " + remains + " карт" + "\n"
                    + "\uD83E\uDD13 Загрузить их?";

            listSendMessages.add(new SendMessage(chatId, sendText).replyMarkup(inlineKeyboardMarkup));
        }

        return listSendMessages;
    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
