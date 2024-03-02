package ru.andrey.ServerAdd.configuration;


import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface Bot extends AutoCloseable, UpdatesListener {

    @Override
    int process(List<Update> updates);

    public void start();

    @Override
    void close();
}
