package ru.andrey.ServerAdd.exceptions;

public class UserNotRegistered extends UserErrorException{
    public UserNotRegistered(Long chatId, String message) {
        super(chatId, message);
    }
}
