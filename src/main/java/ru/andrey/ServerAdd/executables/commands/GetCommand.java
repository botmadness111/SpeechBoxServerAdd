package ru.andrey.ServerAdd.executables.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.services.databases.UserService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class GetCommand {
    private final OkHttpClient client = new OkHttpClient();
    private final String domain = "http://app2:8080/game";

    private final UserService userService;

    @Autowired
    public GetCommand(UserService userService) {
        this.userService = userService;
    }

    public String command() {
        return "/get";
    }

    public String description() {
        return "get card";
    }

    public String commandReg() {
        return "/get";
    }

    public Card handle(Message message) {
        String url = "/get";

        Long chatId = message.chat().id();

        User user = userService.findByTelegramId(chatId.toString()).get();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(domain + url).newBuilder();
        urlBuilder.addQueryParameter("userId", user.getId().toString());

        url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) return null;

            String jsonResponse = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();

            Card card = objectMapper.readValue(jsonResponse, Card.class);

            return card;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
