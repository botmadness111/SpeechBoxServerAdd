package ru.andrey.ServerAdd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tg_user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tg_id")
    private String telegramId;

    @Column(name = "username")
    private String username;

    @Column(name = "stopid")
    private Integer stopId = 0;

    @Column(name = "selectedcardid")
    private Integer selectedCardId = 0;

    @OneToMany(mappedBy = "user")
    private List<Card> cards = new ArrayList<>();

    public User(String telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    public void removeCard(Card card){
        cards.remove(card);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(telegramId, user.telegramId) && Objects.equals(username, user.username) && Objects.equals(stopId, user.stopId) && Objects.equals(selectedCardId, user.selectedCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, username, stopId, selectedCardId, cards);
    }
}
