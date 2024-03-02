package ru.andrey.ServerAdd.SpeechBoxTelegramBot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.andrey.ServerAdd.commands.Command;
import ru.andrey.ServerAdd.commands.Commands;
import ru.andrey.ServerAdd.configuration.Bot;


import java.util.List;

@Component
public class SpeechBoxTelegramBot implements Bot {
    private final TelegramBot bot;
    private final Commands commands;

    @Autowired
    public SpeechBoxTelegramBot(TelegramBot bot, Commands commands) {
        this.bot = bot;
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
                Command command = commands.getCommands().stream().filter(commandI -> commandI.supports(update)).findFirst().orElse(null);

                SendMessage sendMessage;
                if (command != null) sendMessage = command.handle(update);
                else sendMessage = new SendMessage(update.message().chat().id(), "error command");

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
