package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.FavoritesKey;

public interface FavoritesRepository extends JpaRepository<Favorites, FavoritesKey> {
}