package ru.andrey.ServerAdd.SpeechBoxTelegramBot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import redis.clients.jedis.Jedis;
import ru.andrey.ServerAdd.exceptions.CardErrorException;
import ru.andrey.ServerAdd.executables.callbacks.CallBack;
import ru.andrey.ServerAdd.executables.commands.Command;
import ru.andrey.ServerAdd.configuration.Bot;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class SpeechBoxTelegramBot implements Bot {
    private final TelegramBot bot;
    private final List<CallBack> callBacks;
    private final List<Command> commands;
    private final ExecutorService executorService;

    @Autowired
    public SpeechBoxTelegramBot(TelegramBot bot, List<CallBack> callBacks, List<Command> commands, ExecutorService executorService) {
        this.bot = bot;
        this.callBacks = callBacks;
        this.commands = commands;
        this.executorService = executorService;
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
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Command command = null;
                        CallBack callback = null;
                        if (update.message() != null)
                            command = commands.stream().filter(commandI -> commandI.supports(update.message().text())).findFirst().orElse(null);
                        if (update.callbackQuery() != null)
                            callback = callBacks.stream().filter(callBackI -> callBackI.supports(update.callbackQuery().data())).findFirst().orElse(null);

                        List<SendMessage> listSendMessage;
                        if (command != null) listSendMessage = command.handle(update.message());
                        else if (callback != null) listSendMessage = callback.handle(update.callbackQuery());
                        else {
                            Long chatId = null;
                            if (update.message() != null) chatId = update.message().chat().id();
                            else if (update.callbackQuery() != null) chatId = update.callbackQuery().from().id();
                            listSendMessage = Collections.singletonList(new SendMessage(chatId, "\uD83D\uDE35\u200D\uD83D\uDCAB error command"));
                        }

                        for (SendMessage sendMessage : listSendMessage) {
                            bot.execute(sendMessage);
                        }

                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    } catch (CardErrorException e) {
                        bot.execute(new SendMessage(e.getChatId(), e.getMessage()));
                    } catch (RuntimeException e) {
                        System.out.println("Error");
                        System.out.println(e.getMessage());
                    }
                }
            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}

