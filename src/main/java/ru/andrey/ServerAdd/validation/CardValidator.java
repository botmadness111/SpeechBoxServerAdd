package ru.andrey.ServerAdd.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.services.databases.CardService;

@Component
public class CardValidator implements Validator {
    private final CardService cardService;

    public CardValidator(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Card.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Card card = (Card) target;

        if (card.getOriginal().length() > 30 || card.getOriginal().isEmpty()) {
            errors.rejectValue("original", "", "⚠\uFE0F Оригинал должен быть от 1 до 30 символов");
            return;
        }

        if (card.getTranslation().length() > 30 || card.getTranslation().isEmpty()) {
            errors.rejectValue("translation", "", "⚠\uFE0F Перевод должен быть от 1 до 30 символов");
            return;
        }

        if (card.getNameCategory().length() > 30 || card.getNameCategory().isEmpty()) {
            errors.rejectValue("category", "", "\uD83D\uDE43 Категория должна быть от 1 до 30 символов");
            return;
        }

        if (cardService.findByOriginalAndTranslationAndCategoryAndUser(card.getOriginal(), card.getTranslation(), card.getCategory(), card.getUser()).isPresent()) {
            errors.rejectValue("original", "", "\uD83D\uDE36\u200D\uD83C\uDF2B\uFE0F Такая карточка уже есть");
            return;
        }
    }
}
