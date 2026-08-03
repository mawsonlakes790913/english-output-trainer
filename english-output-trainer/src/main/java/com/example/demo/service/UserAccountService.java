package com.example.demo.service;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Users;
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
	    System.out.println("検索するuserId=" + userId);
	    Optional<Users> option = userRepository.findByUserId(userId);
	    
	    if (option.isPresent()) {
	        System.out.println("検索結果=" + option.get().getUserId());
	    } else {
	        System.out.println("検索結果=null");
	    }
	    
	    return option.orElse(null);
	}
	
	@Transactional
	public void updateUserId(String currentUserId, String newUserId) {

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

	}
	
	@Transactional
	public void updateUserPassword(String userId, String currentPassword, String newPassword) {
		
	    // 現在のユーザーを取得
	    Users user = getUserOne(userId);
	    if (user == null) {
	        throw new IllegalArgumentException("ユーザーが存在しません");
	    }
	    
	    // 現在のパスワードが一致するか確認
	    boolean isMatch =
	            passwordEncoder.matches(currentPassword, user.getPassword());

	    if (!isMatch) {
	        throw new IllegalArgumentException("現在のパスワードが正しくありません");
	    }
	    // パスワードをハッシュ化して更新
	    user.setPassword(passwordEncoder.encode(newPassword));

	    // 更新
	    userRepository.save(user);

	}
	
	// 指定したユーザー削除(会員用)
	@Transactional
	public void cancelMembership(String userId) {
		userRepository.deleteByUserId(userId);
        log.info("削除対象={}", userId);
    }
}