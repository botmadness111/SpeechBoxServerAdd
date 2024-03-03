package ru.andrey.ServerAdd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "original")
    private String original;
    @Column(name = "translation")
    private String translation;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User user;

    public Card(String original, String translation, User user) {
        this.original = original;
        this.translation = translation;
        this.user = user;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
