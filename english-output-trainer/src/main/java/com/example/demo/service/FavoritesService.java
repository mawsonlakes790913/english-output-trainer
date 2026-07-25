package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	private final UserService userService;
	private final FavoritesRepository favoritesRepository;
	
	public boolean toggleFavorite(String loginUser, long questionId) {
		
	    Users user = userService.getUserOne(loginUser);
		
		FavoritesKey key = createFavoritesKey(loginUser, questionId);
		
		// 存在確認とINSERT及びDELETE処理
		Optional<Favorites> optionalFavorites =
				favoritesRepository.findByFavoritesKey(key);
		
		if (optionalFavorites.isEmpty()) {
			//ここでINSERT
	        Question question = new Question();
	        question.setQuestionId(questionId);

	        Favorites favorite = new Favorites();
	        favorite.setFavoritesKey(key);
	        favorite.setUser(user);
	        favorite.setQuestion(question);
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
		Users user = userService.getUserOne(loginUser);
		
		// 複合キー情報を取得
		FavoritesKey key = new FavoritesKey();
		key.setUserId(user.getId());
		key.setQuestionId(questionId);
		
		return key;
		
	}
	
	public Page<Question> getFavoritesList(Long userId, Pageable pageable) {
		
		Page <Question> favoritesList = favoritesRepository.getFavoritesList(userId, pageable);
		return favoritesList;
	}
	
	
	
}