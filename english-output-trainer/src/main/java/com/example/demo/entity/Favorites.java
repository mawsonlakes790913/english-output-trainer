package com.example.demo.entity;


import jakarta.persistence.EmbeddedId;


public class Favorites {
	@EmbeddedId
	private FavoritesKey favoritesKey;
	
}