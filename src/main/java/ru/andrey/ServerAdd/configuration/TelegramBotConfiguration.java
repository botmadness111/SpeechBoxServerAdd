package ru.andrey.ServerAdd.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TelegramBotConfiguration {
    private final SpeechBoxTelegramBotSettings speechBoxTelegramBotSettings;

    @Autowired
    public TelegramBotConfiguration(SpeechBoxTelegramBotSettings speechBoxTelegramBotSettings) {
        this.speechBoxTelegramBotSettings = speechBoxTelegramBotSettings;
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(speechBoxTelegramBotSettings.getToken());
    }

}
