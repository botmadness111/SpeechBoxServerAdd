package ru.andrey.ServerAdd.exceptions;

public class CardNotRegistered extends CardErrorException {

    public CardNotRegistered(Long chatId, Integer cardId) {
        super(chatId, "\uD83D\uDE14 Не нашел такую карту");
    }
}
