package ru.andrey.ServerAdd.SpeechBoxTelegramBot;


import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.andrey.ServerAdd.commands.CallBack;
import ru.andrey.ServerAdd.commands.CallBacks;
import ru.andrey.ServerAdd.commands.Command;
import ru.andrey.ServerAdd.commands.Commands;
import ru.andrey.ServerAdd.configuration.Bot;


import java.util.List;

@Component
public class SpeechBoxTelegramBot implements Bot {
    private final TelegramBot bot;
    private final Commands commands;
    private final CallBacks callBacks;

    @Autowired
    public SpeechBoxTelegramBot(TelegramBot bot, Commands commands, CallBacks callBacks) {
        this.bot = bot;
        this.commands = commands;
        this.callBacks = callBacks;
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
            try {
                Command command = commands.getCommands().stream().filter(commandI -> commandI.supports(update)).findFirst().orElse(null);
                CallBack callback = callBacks.getCallBacks().stream().filter(callBackI -> callBackI.supports(update.callbackQuery())).findFirst().orElse(null);

                SendMessage sendMessage;
                if (command != null) sendMessage = command.handle(update);
                else if (callback != null) sendMessage = callback.handle(update.callbackQuery());
                else{
                    Long chatId = null;
                    if (update.message() != null) chatId = update.message().chat().id();
                    else if (update.callbackQuery() != null) chatId = update.callbackQuery().from().id();
                    sendMessage = new SendMessage(chatId, "error command");
                }

                bot.execute(sendMessage);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        }


        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
