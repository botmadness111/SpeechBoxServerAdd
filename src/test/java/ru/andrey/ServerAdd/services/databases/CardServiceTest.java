package ru.andrey.ServerAdd.services.databases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.CardRepository;
import ru.andrey.ServerAdd.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    private static Card card;
    private static User user;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);

        card = new Card("card", "карточка", user);
        card.setId(1);
    }

    @Test
    void save_shouldSaveCard_whenRelevant() {
        when(cardRepository.save(card)).thenReturn(card);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        final Card actual = cardService.save(card);

        assertNotNull(actual);
        assertEquals(card, actual);
        verify(cardRepository).save(card);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void findByOriginalAndTranslation_shouldFindCard_whenExists() {
        List<Card> cards = Collections.singletonList(card);
        when(cardRepository.findByOriginalAndTranslationAndUser(card.getOriginal(), card.getTranslation(), card.getUser()))
                .thenReturn(cards);

        final Card actual = cardService.findByOriginalAndTranslation(card.getOriginal(), card.getTranslation(), card.getUser()).orElse(null);

        assertNotNull(actual);
        assertEquals(card, actual);
        verify(cardRepository).findByOriginalAndTranslationAndUser(card.getOriginal(), card.getTranslation(), card.getUser());
    }

    @Test
    void delete_shouldDeleteCard_whenExists() {
        cardService.delete(card.getOriginal(), card.getTranslation(), card.getNameCategory(), card.getUser());

        verify(cardRepository).deleteCardByOriginalAndTranslationAndCategoryAndUser(card.getOriginal(), card.getTranslation(), card.getNameCategory(), card.getUser());
    }

    @Test
    void setCategory_shouldSetCategory_whenExists() {
        when(cardRepository.findById(card.getId())).thenReturn(Optional.ofNullable(card));
        when(cardRepository.save(card)).thenReturn(card);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        cardService.setCategory(card, "newCategory");


        verify(cardRepository).findById(card.getId());
        verify(cardRepository).save(card);
        verify(userRepository, times(2)).findById(user.getId());
    }

    @Test
    void findByOriginalAndTranslationAndCategoryAndUser_shouldFindCard_whenExists() {
        List<Card> cards = Collections.singletonList(card);
        when(cardRepository.findByOriginalAndTranslationAndCategoryAndUser(card.getOriginal(), card.getTranslation(), card.getCategory(), card.getUser()))
                .thenReturn(cards);

        final Card actual = cardService.findByOriginalAndTranslationAndCategoryAndUser(card.getOriginal(), card.getTranslation(), card.getCategory(), card.getUser()).orElse(null);

        assertNotNull(actual);
        assertEquals(card, actual);
        verify(cardRepository).findByOriginalAndTranslationAndCategoryAndUser(card.getOriginal(), card.getTranslation(), card.getCategory(), card.getUser());

    }

    @Test
    void findByIdGreaterThan_shouldFindCards_whenExists() {
        List<Card> cardsFind = Collections.singletonList(new Card());
        when(cardRepository.findByIdGreaterThanAndUser(0, user.getId())).thenReturn(cardsFind);

        List<Card> actual = cardService.findByIdGreaterThan(0, user.getId());

        assertNotNull(actual);
        assertEquals(actual, cardsFind);
        verify(cardRepository).findByIdGreaterThanAndUser(0, user.getId());
    }

    @Test
    void findById_shouldFindCard_whereExists() {
        when(cardRepository.findById(card.getId())).thenReturn(Optional.ofNullable(card));

        Card actual = cardService.findById(card.getId()).orElse(null);

        assertNotNull(actual);
        assertEquals(card, actual);
        verify(cardRepository).findById(card.getId());
    }

    @Test
    void findCardWithMaxId_shouldFindCard_whenExists() {
        Integer maxId = 1;
        when(cardRepository.findCardWithMaxId(card.getUser().getId())).thenReturn(maxId);

        Integer actual = cardService.findCardWithMaxId(card.getUser().getId());

        assertNotNull(actual);
        assertEquals(maxId, actual);
        verify(cardRepository).findCardWithMaxId(card.getUser().getId());
    }

    @Test
    void countCardByIdLessThan_shouldReturnNumberGreater0_whenExists() {
        Integer countCard = 1;
        when(cardRepository.countCardByIdLessThanAndUser(card.getId(), user)).thenReturn(countCard);

        Integer actual = cardService.countCardByIdLessThan(card.getId(), user);

        assertNotNull(actual);
        assertEquals(actual, countCard);
        verify(cardRepository).countCardByIdLessThanAndUser(card.getId(), user);
    }
}