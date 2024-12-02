package ru.andrey.ServerAdd.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import redis.clients.jedis.Jedis;

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

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(1000);
    }

    @Bean
    public Jedis jedis() {
        return new Jedis("redis", 6379);
    }
}
