package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.FavoritesKey;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.repository.FavoritesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritesService {
	
	private final FavoritesRepository favoritesRepository;
	
	public boolean toggleFavorite(Users user, long questionId) {
		
		FavoritesKey key = createFavoritesKey(user, questionId);
		
		// 存在確認とINSERT及びDELETE処理
		Optional<Favorites> optionalFavorites =
				favoritesRepository.findByFavoritesKey(key);
		
		if (optionalFavorites.isEmpty()) {
			
	        log.info("お気に入り追加開始 userId={}, questionId={}",
	                 user.getId(), questionId);
			//ここでINSERT
	        Question question = new Question();
	        question.setQuestionId(questionId);

	        Favorites favorite = new Favorites();
	        favorite.setFavoritesKey(key);
	        favorite.setUser(user);
	        favorite.setQuestion(question);
	        favorite.setCreatedAt(LocalDateTime.now());

	        favoritesRepository.save(favorite);
	        
	        log.info("お気に入り追加完了 userId={}, questionId={}",
	                 user.getId(), questionId);
	        
	        return true;
		    
		} else {
	        log.info("お気に入り解除開始 userId={}, questionId={}",
	                 user.getId(), questionId);

	        favoritesRepository.deleteById(key);

	        log.info("お気に入り解除完了 userId={}, questionId={}",
	                 user.getId(), questionId);
			return false;

		}
	}
	
	public boolean isFavorite(Users user, long questionId) {
		
		FavoritesKey key = createFavoritesKey(user, questionId);
		
		return favoritesRepository.existsById(key);
	}
	

	private FavoritesKey createFavoritesKey(Users user, long questionId) {
	
	// 複合キー情報を取得
	FavoritesKey key = new FavoritesKey();
	key.setUserId(user.getId());
	key.setQuestionId(questionId);
	
	return key;
	}
}