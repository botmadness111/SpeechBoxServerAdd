package ru.andrey.ServerAdd.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardRepositoryTest {
    private static final Integer ID = 1;

    @Mock
    private CardRepository cardRepositoryMock;

    @Test
    void deleteCardByOriginalAndTranslationAndCategoryAndUser() {
    }

    @Test
    void findByOriginalAndTranslationAndCategoryAndUser_shouldFindCard_whenExists() {

    }

    @Test
    void findByOriginalAndTranslationAndUser() {
        // Создание mock-объекта карточки
        Card card = new Card("translation", "перевод", new User());

        // Создание mock-списка карточек
        List<Card> expectedCards = Collections.singletonList(card);
        when(cardRepositoryMock.findByOriginalAndTranslationAndUser(card.getOriginal(), card.getTranslation(), card.getUser())).thenReturn(expectedCards);

        // Вызов метода, который тестируем
        List<Card> actualCards = cardRepositoryMock.findByOriginalAndTranslationAndUser(card.getOriginal(), card.getTranslation(), card.getUser());

        // Проверка результатов
        assertEquals(expectedCards, actualCards);
    }

    @Test
    void findByIdGreaterThanAndUser() {
    }

    @Test
    void findCardWithMaxId() {
    }

    @Test
    void countCardByIdLessThanAndUser() {
    }

    @Test
    void findByIdAndUser() {
    }

    @Test
    void findAllByUser() {
    }
}