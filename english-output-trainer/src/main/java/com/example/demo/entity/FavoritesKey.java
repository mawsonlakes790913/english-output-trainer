package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FavoritesKey implements Serializable {
    private Long userId;
    private Long questionId;
}