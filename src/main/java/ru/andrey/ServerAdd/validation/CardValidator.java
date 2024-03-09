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

        if (card.getOriginal().length() > 30) {
            errors.rejectValue("original", "", "⚠\uFE0Foriginal should be between 1 and 30");
            return;
        }

        if (card.getTranslation().length() > 30) {
            errors.rejectValue("translation", "", "⚠\uFE0Ftranslation should be between 1 and 30");
            return;
        }

        if (cardService.findByOriginalAndTranslationAndCategory(card.getOriginal(), card.getTranslation(), card.getCategory(), card.getUser()).isPresent()){
            errors.rejectValue("original", "", "⚠\uFE0FТакая карточка уже есть");
            return;
        }
    }
}
