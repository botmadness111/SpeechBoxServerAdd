package ru.andrey.ServerAdd.services.databases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.CardRepository;
import ru.andrey.ServerAdd.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void delete(String original, String translation) {
        cardRepository.deleteCardByOriginalAndTranslation(original, translation);
    }

    public Optional<Card> findByOriginalAndTranslation(String original, String translation) {
        List<Card> cards = cardRepository.findByOriginalAndTranslation(original, translation);
        if (cards.isEmpty()) return Optional.empty();
        else return Optional.of(cards.get(0));
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }
}
