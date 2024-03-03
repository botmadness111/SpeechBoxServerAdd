package ru.andrey.ServerAdd.commands;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CallBacks {
    @Getter
    private final List<CallBack> callBacks;

    public CallBacks(List<CallBack> callBacks) {
        this.callBacks = callBacks;
    }
}
