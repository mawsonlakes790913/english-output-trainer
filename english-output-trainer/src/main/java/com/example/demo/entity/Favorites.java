package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Favorites {
	@EmbeddedId
	private FavoritesKey favoritesKey;
	
	private LocalDateTime createdAt;
	
}