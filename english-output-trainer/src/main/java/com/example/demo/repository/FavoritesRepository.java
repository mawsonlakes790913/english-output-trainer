package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.FavoritesKey;
import com.example.demo.entity.Question;

public interface FavoritesRepository extends JpaRepository<Favorites, FavoritesKey> {
	
	Optional<Favorites> findByFavoritesKey(FavoritesKey favoritesKey);
	
	// 自分でDELETE処理を書く場合
	//void deleteByFavoritesKey(FavoritesKey favoritesKey);
	
//	@Query(value = """
//			SELECT q.*
//			FROM favorites f
//			JOIN question q
//			ON f.question_id = q.question_id
//			WHERE f.user_id = :userId
//			ORDER BY f.created_at
//			""", nativeQuery = true)
//			List<Question> getFavoritesList(
//					@Param("userId") Long userId
//					);
	
	@Query("""
		    SELECT f.question
		    FROM Favorites f
		    WHERE f.user.id = :userId
		    ORDER BY f.createdAt
		    """)
		Page<Question> getFavoritesList(
		        @Param("userId") Long userId,
		        Pageable pageable
		);
	
}