package ru.andrey.ServerAdd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private Integer stopId;

    @Column(name = "selectedcardid")
    private Integer selectedCardId;

    @OneToMany(mappedBy = "user")
    private List<Card> cards = new ArrayList<>();

    public User(String telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
