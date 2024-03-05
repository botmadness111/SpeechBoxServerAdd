package ru.andrey.ServerAdd.services.databases;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.ServerAdd.dao.UserDAO;
import ru.andrey.ServerAdd.model.User;
import ru.andrey.ServerAdd.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserRepository userRepository, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.userDAO = userDAO;
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
}
