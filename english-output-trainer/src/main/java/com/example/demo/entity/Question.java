package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "japanese_text")
    private String japaneseText;

    @Column(name = "english_text")
    private String englishText;

    @Column(name = "alternative_answer")
    private String alternativeAnswer;

    @Column(name = "condition")
    private String condition;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "question")
    private List<Favorites> favorites;
}