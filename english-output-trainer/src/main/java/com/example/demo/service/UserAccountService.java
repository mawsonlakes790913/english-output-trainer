package com.example.demo.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Users;
import com.example.demo.exception.CurrentPasswordMismatchException;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Users getUserOne(String userId) {

	    log.debug("ユーザー検索 userId={}", userId);

	    return userRepository.findByUserId(userId)
	            .orElse(null);
	}
	
	@Transactional
	public void updateUserId(String currentUserId, String newUserId) {
		
		log.info("ユーザーID変更開始 currentUserId={}, newUserId={}",
		        currentUserId, newUserId);

	    // 新しいユーザーIDが既に使われているか確認
		boolean isExists = userRepository.existsByUserId(newUserId);
	    if (isExists) {
	        throw new DuplicateKeyException("既に存在するユーザーです");
	    }
	    // 現在のユーザーを取得
	    Users user = getUserOne(currentUserId);
	    if (user == null) {
	        throw new IllegalArgumentException("ユーザーが存在しません");
	    }

	    // userIdだけ変更
	    user.setUserId(newUserId);

	    // 更新
	    userRepository.save(user);
	    
	    log.info("ユーザーID変更完了 currentUserId={}, newUserId={}",
	            currentUserId, newUserId);

	}
	
	@Transactional
	public void updateUserPassword(String userId, String currentPassword, String newPassword) {
		
		log.info("パスワード変更開始 userId={}", userId);
		
	    // 現在のユーザーを取得
	    Users user = getUserOne(userId);
	    if (user == null) {
	        throw new IllegalArgumentException("ユーザーが存在しません");
	    }
	    
	    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
	        throw new CurrentPasswordMismatchException("現在のパスワードが正しくありません");
	    }
	    
	    // パスワードをハッシュ化して更新
	    user.setPassword(passwordEncoder.encode(newPassword));

	    // 更新
	    userRepository.save(user);

		log.info("パスワード変更完了 userId={}", userId);

	}
	
	// 指定したユーザー削除(会員用)
	@Transactional
	public void cancelMembership(String userId) {
		log.info("退会開始 userId={}", userId);

		userRepository.deleteByUserId(userId);

		log.info("退会完了 userId={}", userId);
    }
}