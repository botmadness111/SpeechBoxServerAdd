package ru.andrey.ServerAdd.services.databases;

import org.springframework.stereotype.Service;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.repositories.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }
}
