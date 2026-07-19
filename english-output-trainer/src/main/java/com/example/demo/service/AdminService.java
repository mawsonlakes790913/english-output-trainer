package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Question;
import com.example.demo.form.QuestionForm;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;
	
	@Transactional
	public void deleteOneUser(String userId) {
		userRepository.deleteByUserId(userId);
	}
	
	public void addQuestion(QuestionForm form) {
		
    	Question question = new Question();

    	question.setJapaneseText(form.getJapaneseText());
    	question.setEnglishText(form.getEnglishText());
    	question.setAlternativeAnswer(form.getAlternativeAnswer());
    	question.setCondition(form.getCondition());
    	question.setDifficulty(form.getDifficulty());
    	
    	Question savedQuestion = questionRepository.save(question);
    	
    	log.info("問題登録完了 questionId={}", savedQuestion.getQuestionId());
		
	}
		
}