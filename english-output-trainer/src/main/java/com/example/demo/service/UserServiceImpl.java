package com.example.demo.service;

import org.springframework.dao.DuplicateKeyException;
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
	
	public void signup(Users users) {
		boolean isExists = repository.existsById(users.getUserId());
        if (isExists) {
            throw new DuplicateKeyException("既に存在するユーザーです");
        }
    Users savedUser = repository.save(users);

    log.info("ユーザー登録完了 userId={}",
             savedUser.getUserId());
	}
	// AOP動作確認用の例外
	//public void testException() {
	//    throw new RuntimeException("AOP動作確認用の例外");
	//}
}