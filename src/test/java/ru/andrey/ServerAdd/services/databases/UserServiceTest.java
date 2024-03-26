package ru.andrey.ServerAdd.services.databases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static User user;

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CardService cardServiceMock;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setTelegramId("1");
    }

    @Test
    void save_shouldReturnUser_whenRelevantSave() {
        when(userRepositoryMock.save(user)).thenReturn(user);

        User actual = userService.save(user);

        assertNotNull(actual);
        assertEquals(actual, user);
        verify(userRepositoryMock).save(user);
    }

    @Test
    void findByTelegramId_shouldFindUser_whenExists() {
        when(userRepositoryMock.findByTelegramId(user.getTelegramId())).thenReturn(Optional.ofNullable(user));

        User actual = userService.findByTelegramId(user.getTelegramId()).orElse(null);

        assertNotNull(actual);
        assertEquals(actual, user);
        verify(userRepositoryMock).findByTelegramId(user.getTelegramId());
    }

    @Test
    void findByIdWithCards_shouldFindUserWithCards_whenExists() {
        List<Card> cardList = Collections.singletonList(new Card());
        user.setCards(cardList);

        when(userRepositoryMock.findByTelegramId(user.getTelegramId())).thenReturn(Optional.of(user));

        User actual = userService.findByTelegramIdWithCards(user.getTelegramId());

        assertNotNull(actual);
        assertEquals(actual, user);
        verify(userRepositoryMock).findByTelegramId(user.getTelegramId());

    }

    @Test
    void setStopId() {
        Integer curId = 0;
        Integer maxId = 1;

        when(userRepositoryMock.findByTelegramId(user.getTelegramId())).thenReturn(Optional.of(user));
        when(cardServiceMock.findCardWithMaxId(user.getId())).thenReturn(maxId);

        userService.setStopId(user, curId);

        verify(userRepositoryMock).findByTelegramId(user.getTelegramId());
        verify(cardServiceMock).findCardWithMaxId(user.getId());
        verify(userRepositoryMock).save(user);
    }

    @Test
    void setSelectedCardId() {
        int cartId = 0;

        when(userService.findByTelegramId(user.getTelegramId())).thenReturn(Optional.of(user));

        userService.setSelectedCardId(user, cartId);

        verify(userRepositoryMock).save(user);

    }
}