package ru.andrey.ServerAdd.executables.callbacks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import ru.andrey.ServerAdd.executables.Executable;

import java.util.List;

public interface CallBack extends Executable {

    List<SendMessage> handle(CallbackQuery callbackQuery);

    Boolean supports(CallbackQuery callbackQuery);
}
