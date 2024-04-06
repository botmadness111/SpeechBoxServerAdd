package ru.andrey.ServerAdd.repositories;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@DataJpaTest
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardRepositoryTest {
    private static final Integer ID = 1;

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private User userMock;
    private Card cardMock;

    @BeforeEach
    public void setUp() {
        userMock = userRepository.findByTelegramId("1234").orElse(null);
        if (userMock == null) {
            userMock = new User("1234", "TestUser");
            userMock = userRepository.save(userMock);
        }

        if (!cardRepository.findByOriginalAndTranslationAndCategoryAndUser("Test", "Тест", null, userMock).isEmpty())
            cardMock = cardRepository.findByOriginalAndTranslationAndCategoryAndUser("Test", "Тест", null, userMock).get(0);

        if (cardMock == null) {
            cardMock = new Card("Test", "Тест", userMock);
            cardRepository.save(cardMock);
        }
        if (!userMock.getCards().contains(cardMock))
            userMock.addCard(cardMock);
    }

    @AfterEach
    public void tearDown() {
        if (cardRepository.findById(cardMock.getId()).isPresent()) {
            cardRepository.delete(cardMock);
            cardMock.getUser().removeCard(cardMock);
        }
        if (userRepository.findById(userMock.getId()).isPresent())
            userRepository.delete(userMock);

        cardMock = null;
        userMock = null;
    }

    @Autowired
    CardRepositoryTest(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Test
    void deleteCardByOriginalAndTranslationAndCategoryAndUser_shouldDeleteCard_whenExists() {
        cardRepository.delete(cardMock);
        cardMock.getUser().getCards().remove(cardMock);

        assertNull(cardRepository.findById(cardMock.getId()).orElse(null));
        assertFalse(cardMock.getUser().getCards().contains(cardMock));
    }

    @Test
    void findByOriginalAndTranslationAndCategoryAndUser_shouldFindCard_whenExists() {
        Card actual = cardRepository.findByOriginalAndTranslationAndCategoryAndUser(
                cardMock.getOriginal(), cardMock.getTranslation(), cardMock.getCategory(), cardMock.getUser()
        ).get(0);

        assertNotNull(actual);
        assertEquals(cardMock, actual);
    }

    @Test
    void findByOriginalAndTranslationAndUser_shouldFindCard_whenExists() {
        Card actual = cardRepository.findByOriginalAndTranslationAndUser(
                cardMock.getOriginal(), cardMock.getTranslation(), cardMock.getUser()
        ).get(0);

        assertNotNull(actual);
        assertEquals(cardMock, actual);
    }

    @Test
    void findByIdGreaterThanAndUser_shouldFindCards_whenExists() {
        Card cardCurrent = cardRepository.findById(1).get();

        List<Card> cardList = new ArrayList<>(List.of(cardRepository.findById(2).get()));
        List<Card> actual = cardRepository.findByIdGreaterThanAndUser(cardCurrent.getId(), cardCurrent.getUser().getId());

        assertNotNull(actual);
        assertIterableEquals(actual, cardList);
    }

    @Test
    void findCardWithMaxId_shouldFindCard_whenExists() {
        Integer expected = 2;
        Integer actual = cardRepository.findCardWithMaxId(4);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    void countCardByIdLessThanAndUser_shouldFindCards_whenExists() {
        Integer expected = 1;

        Card cardCurrent = cardRepository.findById(2).get();
        Integer actual = cardRepository.countCardByIdLessThanAndUser(cardCurrent.getId(), cardCurrent.getUser());

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdAndUser_shouldFindCard_whenExists() {
        Card actual = cardRepository.findById(cardMock.getId()).get();

        assertNotNull(actual);
        assertEquals(cardMock, actual);
    }

    @Test
    void findAllByUser_shouldFindCards_whenExists() {
    }
}