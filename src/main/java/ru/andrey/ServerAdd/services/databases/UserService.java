package ru.andrey.ServerAdd.services.databases;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final CardService cardService;

    @Autowired
    public UserService(UserRepository userRepository, CardService cardService) {
        this.userRepository = userRepository;
        this.cardService = cardService;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByTelegramId(String tg_id) {
        return userRepository.findByTelegramId(tg_id);
    }

    public User findByIdWithCards(Integer userId) {
        User userFind = userRepository.findById(userId).orElse(null);

        if (userFind != null) {
            Hibernate.initialize(userFind.getCards());
        }

        return userFind;

    }

    @Transactional
    public void setStopId(User user, Integer value) {
        user = findByTelegramId(user.getTelegramId()).get();
        user.setStopId(value >= cardService.findCardWithMaxId(user.getId()) ? 0 : value);
        userRepository.save(user);
    }

    @Transactional
    public void setSelectedCardId(User user, int id) {
        user = findByTelegramId(user.getTelegramId()).get();
        user.setSelectedCardId(id);
        userRepository.save(user);
    }
}
