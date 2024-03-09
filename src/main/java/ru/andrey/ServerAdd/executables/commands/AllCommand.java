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
        return "send all cards";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        Long chatId = message.chat().id();
        String text = message.text();

        User user = userService.findByTelegramId(chatId.toString()).get();
        List<Card> cardsRemains = cardService.findByIdGreaterThan(user.getStopId());

        List<SendMessage> listSendMessages = new ArrayList<>();
        Integer startPoint = user.getStopId();
        for (int cardI = startPoint; cardI < cardsRemains.size(); cardI++) {
            Card card = cardsRemains.get(cardI);
            if (cardI != startPoint && cardI % 5 == 0) {
                InlineKeyboardMarkup inlineKeyboardMarkup = yesNoInlineKeyboardMarkup.getYesNo(command());


                String sendText = "\uD83D\uDC40Осталось еще " + (cardsRemains.size() - cardI) + " карт" + "\n"
                        + "\uD83E\uDD13Загрузить их?";

                listSendMessages.add(new SendMessage(chatId, sendText).replyMarkup(inlineKeyboardMarkup));

                userService.setStopId(user, cardI);
                return listSendMessages;
            }

            InlineKeyboardButton inlineKeyboardButton1 = deleteInlineKeyboardMarkup.getDelete(card.getOriginal(), card.getTranslation());
            InlineKeyboardButton inlineKeyboardButton2 = categoryInlineKeyboardMarkup.getCategory(card.getId());

            String sendText = "\uD83D\uDCDAВаша карточка" + "\n\n"
                    + "Оригинал: " + card.getOriginal() + "\n"
                    + "Перевод: " + card.getTranslation() + "\n"
                    + "Категория: " + card.getNameCategory();

            InlineKeyboardMarkup inlineKeyboardMarkup;
            if (card.getCategory() == null)
                inlineKeyboardMarkup = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{new InlineKeyboardButton[]{inlineKeyboardButton2}, new InlineKeyboardButton[]{inlineKeyboardButton1}});
            else
                inlineKeyboardMarkup = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{new InlineKeyboardButton[]{inlineKeyboardButton1}});

            listSendMessages.add(new SendMessage(chatId, sendText).replyMarkup(inlineKeyboardMarkup));
        }

        userService.setStopId(user, 0);
        return listSendMessages;
    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
