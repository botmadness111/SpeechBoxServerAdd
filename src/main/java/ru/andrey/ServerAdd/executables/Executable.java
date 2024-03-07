package ru.andrey.ServerAdd.executables;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface Executable {
    String command();

    String description();

}
