package ru.andrey.ServerAdd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrey.ServerAdd.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
}
