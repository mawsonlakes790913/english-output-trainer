package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class UserServiceImpl {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	public void signup(Users user) {
		boolean isExists = repository.existsByUserId(user.getUserId());
        if (isExists) {
            throw new DuplicateKeyException("既に存在するユーザーです");
        }
    // 自動でRoleをGeneral(一般)にする
    user.setRole("ROLE_GENERAL");
        
    // パスワードのハッシュ化
    String rawPassword = user.getPassword();
    user.setPassword(passwordEncoder.encode(rawPassword));
    
    System.out.println("id=" + user.getId());
        
    Users savedUser = repository.save(user);

    log.info("ユーザー登録完了 userId={}",
             savedUser.getUserId());
	}
	
	// ユーザー取得
	//public Users getUserOne(String userId) {
	//	Optional<Users> option = repository.findByUserId(userId);
	//	Users user = option.orElse(null);
	//	return user;
	//}
	
	public Users getUserOne(String userId) {
	    System.out.println("検索するuserId=" + userId);

	    Optional<Users> option = repository.findByUserId(userId);

	    //System.out.println("検索結果=" + option);
	    System.out.println("検索結果=" + option.orElse(null).getUserId());

	    return option.orElse(null);
	}

	
	// ユーザー一覧取得
	public List<Users> getUsers(){
		List<Users> users = repository.findAll();
		return users;
	}
	
	// 指定したユーザー削除(Admin用)
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUserOne(String userId) {
        repository.deleteByUserId(userId);
        log.info("削除対象={}", userId);
    }

	// 指定したユーザー削除(会員用)
	@Transactional
	public void cancelMembership(String userId) {
        repository.deleteByUserId(userId);
        log.info("削除対象={}", userId);
    }
	
	@Transactional
	public void updateUserId(String currentUserId, String newUserId) {

	    // 新しいユーザーIDが既に使われているか確認
		boolean isExists = repository.existsByUserId(newUserId);
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
	    repository.save(user);

	}
	
	@Transactional
	public void updateUserPassword(String userId, String currentPassword, String newPassword) {

	    // 新しいパスワードが既に使われているか確認
		//boolean isExists = repository.existsByUserId(newPassword);
	    //if (isExists) {
	    //    throw new DuplicateKeyException("既に存在するパスワードです");
	    //}
		
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
	    repository.save(user);

	}
	
	
}