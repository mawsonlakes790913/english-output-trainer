package com.example.demo.service;



import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class StudyServiceImpl implements StudyService {
	
	private final QuestionRepository repository;
	
	// public Page<Question> getQuestion(Pageable pageable){
		// 問題一覧取得
		// return repository.findAll(pageable);
		
		// Page<Question> questionList = repository.findAll(pageable);
		// MyBatisなら
		// ①まずListへ格納
		// List<Question> questionList = repository.findAll(pageable);
		// ②問題総数取得
		// long count = repository.count();
		// ③Pageのインスタンス生成
		// return new PageImpl<Question>(questionList, pageable, count);
	// }

	//public List<Question> getQuestion(){
		//return repository.findAll();
	//}
	
	public List<Question> getRandomQuestion(){
		
		List<Question> extractedQuestions = repository.findAll();
		Collections.shuffle(extractedQuestions);
		return extractedQuestions;
	}
}