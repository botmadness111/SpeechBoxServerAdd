package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import ru.andrey.ServerAdd.executables.Executable;

public interface CallBack extends Executable {
    String command();

    String description();

    SendMessage handle(CallbackQuery callbackQuery);

    Boolean supports(CallbackQuery callbackQuery);
}
