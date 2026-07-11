package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;


public class Favorites {
	@EmbeddedId
	private FavoritesKey favoritesKey;
	
	private LocalDateTime createdAt;
	
}