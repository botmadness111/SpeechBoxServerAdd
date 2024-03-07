package ru.andrey.ServerAdd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 30, message = "⚠\uFE0Foriginal should be between 1 and 30")
    private String original;
    @Column(name = "translation")
    @Size(min = 1, max = 30, message = "⚠\uFE0Ftranslation should be between 1 and 30")
    private String translation;
    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User user;

    public Card(String original, String translation, User user) {
        this.original = original;
        this.translation = translation;
        this.user = user;
    }

    public Card(String original, String translation, User user, String category) {
        this.original = original;
        this.translation = translation;
        this.user = user;
        this.category = category;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public String getNameCategory() {
        if (category == null) return "не указана";
        return category;
    }
}
