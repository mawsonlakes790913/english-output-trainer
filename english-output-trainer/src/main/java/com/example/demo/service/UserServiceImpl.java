package com.example.demo.service;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	public void signup(Users user) {
		boolean isExists = repository.existsById(user.getUserId());
        if (isExists) {
            throw new DuplicateKeyException("既に存在するユーザーです");
        }
        
    // 自動でRoleをGeneral(一般)にする
    user.setRole("ROLE_GENERAL");
        
    // パスワードのハッシュ化
    String rawPassword = user.getPassword();
    user.setPassword(encoder.encode(rawPassword));
        
    Users savedUser = repository.save(user);

    log.info("ユーザー登録完了 userId={}",
             savedUser.getUserId());
	}
	
	// ユーザー取得
	public Users getUserOne(String userId) {
		Optional<Users> option = repository.findById(userId);
		Users user = option.orElse(null);
		return user;
	}
	// AOP動作確認用の例外
	//public void testException() {
	//    throw new RuntimeException("AOP動作確認用の例外");
	//}
}