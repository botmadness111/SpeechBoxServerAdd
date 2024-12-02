package ru.andrey.ServerAdd.executables.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AnsCommand implements Command {
    private final OkHttpClient client = new OkHttpClient();
    private final String domain = "http://app2:8080/game";

    private final GetCommand getCommand;

    private final UserService userService;

    @Autowired
    public AnsCommand(GetCommand getCommand, UserService userService) {
        this.getCommand = getCommand;
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/ans";
    }

    @Override
    public String description() {
        return "ответить на вопрос";
    }

    @Override
    public String commandReg() {
        return "/ans\\s.*";
    }

    @Override
    public List<SendMessage> handle(Message message) {
        String url = "/result";

        Long chatId = message.chat().id();

        String ansUser = message.text().split(command())[1].strip();

        User user = userService.findByTelegramId(chatId.toString()).get();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(domain + url).newBuilder();
        urlBuilder.addQueryParameter("userId", user.getId().toString());
        urlBuilder.addQueryParameter("input", ansUser);

        url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() != 200) return null;

            String jsonResponse = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();

            Boolean ans = objectMapper.readValue(jsonResponse, Boolean.class);

            Card card = getCommand.handle(message);

            String text;
            if (ans) {
                text = "Верно" + "\n";

                if (card == null) {
                    text += "Игра окончена";
                } else {
                    text += "original: " + card.getOriginal() + "\n"
                            + "translation: " + "||" + card.getTranslation() + "||";
                }

            } else {
                text = "Неверно";
            }

            return Collections.singletonList(new SendMessage(chatId.toString(), text).parseMode(ParseMode.MarkdownV2));
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return null;
        }

    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
