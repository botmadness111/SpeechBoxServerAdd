package ru.andrey.ServerAdd.exceptions;

import lombok.Getter;

@Getter
public class UserErrorException extends RuntimeException {
    private final String message;
    private final Long chatId;

    public UserErrorException(Long chatId, String message) {
        super(message);
        this.message = message;
        this.chatId = chatId;
    }
}
