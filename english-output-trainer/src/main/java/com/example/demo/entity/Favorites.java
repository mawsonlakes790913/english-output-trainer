package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "favorites")
public class Favorites {
	@EmbeddedId
	private FavoritesKey favoritesKey;
	
	private LocalDateTime createdAt;
	
}