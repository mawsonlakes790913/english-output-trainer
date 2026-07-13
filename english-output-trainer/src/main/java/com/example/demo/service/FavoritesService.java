package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.FavoritesKey;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.repository.FavoritesRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FavoritesService {
	
	private final UserServiceImpl userServiceImpl;
	private final FavoritesRepository favoritesRepository;
	
	public boolean toggleFavorite(String loginUser, long questionId) {
		
		FavoritesKey key = createFavoritesKey(loginUser, questionId);
		
		// 存在確認とINSERT及びDELETE処理
		Optional<Favorites> optionalFavorites =
				favoritesRepository.findByFavoritesKey(key);
		
		if (optionalFavorites.isEmpty()) {
			//ここでINSERT
		    Favorites favorite = new Favorites();
		    favorite.setFavoritesKey(key);
		    favorite.setCreatedAt(LocalDateTime.now());

		    favoritesRepository.save(favorite);
		    return true;
		    
		} else {
			//ここでDELETE
			favoritesRepository.deleteById(key);
			return false;

		}
	}
	
	public boolean isFavorite(String loginUser, long questionId) {
		
		FavoritesKey key = createFavoritesKey(loginUser, questionId);
		
		return favoritesRepository.existsById(key);
	}
	
	private FavoritesKey createFavoritesKey(String loginUser, long questionId) {
		
		// ユーザー情報を取得
		Users user = userServiceImpl.getUserOne(loginUser);
		
		// 複合キー情報を取得
		FavoritesKey key = new FavoritesKey();
		key.setUserId(user.getId());
		key.setQuestionId(questionId);
		
		return key;
		
	}
	
	public List<Question> getFavoritesList(Long userId) {
		
		List<Question> favoritesList = favoritesRepository.getFavoritesList(userId);
		return favoritesList;
	}
	
	
	
}