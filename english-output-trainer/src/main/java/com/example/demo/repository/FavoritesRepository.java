package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.FavoritesKey;

public interface FavoritesRepository extends JpaRepository<Favorites, FavoritesKey> {
	
	Optional<Favorites> findByFavoritesKey(FavoritesKey favoritesKey);
	
	void deleteByQuestionQuestionId(Long questionId);
	
}