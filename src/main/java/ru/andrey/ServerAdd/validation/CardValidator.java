package ru.andrey.ServerAdd.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrey.ServerAdd.model.Card;

@Component
public class CardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Card.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Card card = (Card) target;

        if (card.getOriginal().length() > 30){
            errors.rejectValue("original", "", "⚠\uFE0Foriginal should be between 1 and 30");
        }

        if (card.getTranslation().length() > 30){
            errors.rejectValue("translation", "", "⚠\uFE0Ftranslation should be between 1 and 30");
        }
    }
}
