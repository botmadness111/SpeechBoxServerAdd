package ru.andrey.ServerAdd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andrey.ServerAdd.model.Card;
import ru.andrey.ServerAdd.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    void deleteCardByOriginalAndTranslationAndCategoryAndUser(String original, String translation, String category, User user);

    List<Card> findByOriginalAndTranslationAndCategoryAndUser(String original, String translation, String category, User user);

    List<Card> findByOriginalAndTranslationAndUser(String original, String translation, User user);

    @Query(value = "select card from Card card where card.id > :value and card.user.id = :userId order by card.id ASC limit 5")
    List<Card> findByIdGreaterThanAndUser(@Param("value") Integer value, @Param("userId") int userId);

    @Query(value = "select max(card.id) from Card card where card.user.id = :userId")
    Integer findCardWithMaxId(@Param("userId") int userId);

    Integer countCardByIdLessThanAndUser(int id, User user);

    Optional<Card> findByIdAndUser(int id, User user);

    List<Card> findAllByUser(User user);


}
