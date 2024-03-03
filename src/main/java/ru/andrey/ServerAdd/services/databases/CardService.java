package ru.andrey.ServerAdd.services.databases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.CardRepository;
import ru.andrey.ServerAdd.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Card save(Card card) {
        User user = userRepository.findById(card.getUser().getId()).get();

        user.addCard(card);
        card.addUser(user);

        return cardRepository.save(card);
    }
}
