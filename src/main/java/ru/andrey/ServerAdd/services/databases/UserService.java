package ru.andrey.ServerAdd.services.databases;

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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByTelegramId(String tg_id) {
        return userRepository.findByTelegramId(tg_id);
    }
}
