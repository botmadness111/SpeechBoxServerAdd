package ru.andrey.ServerAdd.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@DataJpaTest
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CardRepositoryTest {
    private static final Integer ID = 1;

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Mock
    private CardRepository cardRepositoryMock;

    @Autowired
    CardRepositoryTest(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Test
    void deleteCardByOriginalAndTranslationAndCategoryAndUser() {
    }

    @Test
    void findByOriginalAndTranslationAndCategoryAndUser_shouldFindCard_whenExists() {
        System.out.println(cardRepository.findById(2).get());
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