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
    private final String domain = "http://localhost:8080/game";

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

//            String jsonResponse = response.body().string();
//
//            JSONArray jsonArray = new JSONArray(jsonResponse);


//            List<Card> cardList = new ArrayList<>();
//            ObjectMapper objectMapper = new ObjectMapper();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                try {
//                    // Преобразование JSON объекта в экземпляр класса Card
//                    Card card = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), Card.class);
//
//                    // Добавление экземпляра Card в список
//                    cardList.add(card);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            Card card = getCommand.handle(message);
            String text;
            if (response.code() == 200) {
                text = "Игра началась!" + "\n"
                        + "Для ответа напишите: /ans ответ" + "\n"
                        + "original: " + "**" + card.getOriginal() + "**"    + "\n"
                        + "translation: " + "||" + card.getTranslation() + "||" + "\n"
                        + "**скрытый текст.**";

            } else {
                text = "Ошибочка...";
            }


            return Collections.singletonList(new SendMessage(message.chat().id(), text));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean supports(String commandName) {
        return Pattern.matches(commandReg(), commandName);
    }
}
