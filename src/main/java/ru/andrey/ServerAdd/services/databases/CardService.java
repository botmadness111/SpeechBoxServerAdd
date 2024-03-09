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

    @Transactional
    public void setCategory(Card card, String category) {
        card = findById(card.getId()).get();
        card.setCategory(category);
        save(card);

        User user = userRepository.findById(card.getUser().getId()).get();
        user.setSelectedCardId(null);
    }

    public Optional<Card> findByOriginalAndTranslationAndCategory(String original, String translation, String category, User user) {
        List<Card> cards = cardRepository.findByOriginalAndTranslationAndCategoryAndUser(original, translation, category, user);
        if (cards.isEmpty()) return Optional.empty();
        else return Optional.of(cards.get(0));
    }

    public Optional<Card> findByOriginalAndTranslation(String original, String translation, User user) {
        List<Card> cards = cardRepository.findByOriginalAndTranslationAndUser(original, translation, user);
        if (cards.isEmpty()) return Optional.empty();
        else return Optional.of(cards.get(0));
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public List<Card> findByIdGreaterThan(Integer value, int userId) {
        return cardRepository.findByIdGreaterThanAndUser(value, userId);
    }

    public Optional<Card> findById(int id) {
        return cardRepository.findById(id);
    }

    public Integer findCardWithMaxId() {
        return cardRepository.findCardWithMaxId();
    }

    public Integer countCardByIdLessThan(int id){
        return cardRepository.countCardByIdLessThan(id);
    }
}
