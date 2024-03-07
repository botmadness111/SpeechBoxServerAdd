package ru.andrey.ServerAdd.exceptions;

import lombok.Getter;

@Getter
public class CardErrorException extends RuntimeException {
    private final String message;
    private final Long chatId;

    public CardErrorException(Long chatId, String message) {
        super(message);
        this.message = message;
        this.chatId = chatId;
    }
}
