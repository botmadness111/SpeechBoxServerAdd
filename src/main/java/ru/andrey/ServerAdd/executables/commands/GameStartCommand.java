package ru.andrey.ServerAdd.executables.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
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
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class GameStartCommand implements Command {
    private final OkHttpClient client = new OkHttpClient();
    private final String domain = "http://app2:8080/game";

    private final GetCommand getCommand;

    private final UserService userService;

    @Autowired
    public GameStartCommand(GetCommand getCommand, UserService userService) {
        this.getCommand = getCommand;
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/game";
    }

    @Override
    public String description() {
        return "start game";
    }

    @Override
    public String commandReg() {
        return "/game";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        String url = "/start";

        User user = userService.findByTelegramId(message.chat().id().toString()).get();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(domain + url).newBuilder();
        urlBuilder.addQueryParameter("userId", user.getId().toString());

        url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            Card card = getCommand.handle(message);
            String text;
            if (response.code() == 200) {
                text = "Игра началась" + "\n"
                        + "Для ответа напишите: /ans ответ" + "\n"
                        + "original: " + card.getOriginal() + "\n"
                        + "translation: " + "||" + card.getTranslation() + "||";

            } else {
                text = "Ошибочка...";
            }


            return Collections.singletonList(new SendMessage(message.chat().id(), text).parseMode(ParseMode.MarkdownV2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
