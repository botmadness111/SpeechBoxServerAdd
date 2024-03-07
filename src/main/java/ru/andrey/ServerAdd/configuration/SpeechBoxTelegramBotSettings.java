package ru.andrey.ServerAdd.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:SpeechBoxTelegramBot.properties")
@Getter
@Component
public class SpeechBoxTelegramBotSettings {
    private final String name;
    private final String token;

    public SpeechBoxTelegramBotSettings(@Value(value = "${SpeechBoxTelegramBot.name}") String name, @Value(value = "${SpeechBoxTelegramBot.token}") String token) {
        this.name = name;
        this.token = token;

    }
}
