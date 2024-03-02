package ru.andrey.ServerAdd.SpeechBoxTelegramBot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.andrey.ServerAdd.configuration.Bot;
import ru.andrey.ServerAdd.services.messages.MessageServiceText;


import java.util.List;

@Component
public class SpeechBoxTelegramBot implements Bot {
    private final TelegramBot bot;
    private final MessageServiceText messageServiceText;

    @Autowired
    public SpeechBoxTelegramBot(TelegramBot bot, MessageServiceText messageServiceText) {
        this.bot = bot;
        this.messageServiceText = messageServiceText;
    }


    @PostConstruct
    @Override
    public void start() {
        bot.setUpdatesListener(this);

    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {

            Message message = update.message();

            if (message != null) {
                Long chat_id = update.message().chat().id();
                String text = update.message().text();

                messageServiceText.sendMessage(update);
            } else if (update.callbackQuery() != null){
                System.out.println("dsfdsfs");
            }


//            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                    new InlineKeyboardButton[]{
//                            new InlineKeyboardButton("url").url("www.google.com"),
//                            new InlineKeyboardButton("callback_data").callbackData("callback_data"),
//                            new InlineKeyboardButton("Switch!").switchInlineQuery("switch_inline_query")
//                    });
//
//            Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
//                    new String[]{"add card", "first row button2"},
//                    new String[]{"second row button1", "second row button2"})
//                    .oneTimeKeyboard(true)   // optional
//                    .resizeKeyboard(true)    // optional
//                    .selective(true);        // optional
//
//            SendMessage sendMessage = new SendMessage(chat_id, message)
//                    .replyMarkup(inlineKeyboard);
//
//            bot.execute(sendMessage);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }


        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
