package ru.andrey.ServerAdd.SpeechBoxTelegramBot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.andrey.ServerAdd.executables.callbacks.CallBack;
import ru.andrey.ServerAdd.executables.commands.Command;
import ru.andrey.ServerAdd.configuration.Bot;


import java.util.List;

@Component
public class SpeechBoxTelegramBot implements Bot {
    private final TelegramBot bot;
    private final List<CallBack> callBacks;
    private final List<Command> commands;

    @Autowired
    public SpeechBoxTelegramBot(TelegramBot bot, List<CallBack> callBacks, List<Command> commands) {
        this.bot = bot;
        this.callBacks = callBacks;
        this.commands = commands;
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
                Command command = commands.stream().filter(commandI -> commandI.supports(update)).findFirst().orElse(null);
                CallBack callback = callBacks.stream().filter(callBackI -> callBackI.supports(update.callbackQuery())).findFirst().orElse(null);

                SendMessage sendMessage;
                if (command != null) sendMessage = command.handle(update);
                else if (callback != null) sendMessage = callback.handle(update.callbackQuery());
                else {
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
