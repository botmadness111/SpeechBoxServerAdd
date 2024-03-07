package ru.andrey.ServerAdd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrey.ServerAdd.model.Card;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    void deleteCardByOriginalAndTranslation(String original, String translation);

    List<Card> findByOriginalAndTranslationAndCategory(String original, String translation, String category);

    List<Card> findByOriginalAndTranslation(String original, String translation);
}
