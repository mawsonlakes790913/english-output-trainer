package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.QuestionDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;
import com.example.demo.entity.Users;
import com.example.demo.form.QuestionForm;
import com.example.demo.repository.FavoritesRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudyHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SearchConditionConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;
	private final SearchConditionConverter searchConditionConverter;
	private final FavoritesRepository favoritesRepository;
	private final StudyHistoryRepository studyHistoryRepository;
	
	// 指定したユーザー削除(Admin用)
	@Transactional
	public void deleteOneUser(String userId) {

	    userRepository.deleteByUserId(userId);

	    log.info("ユーザー削除 userId={}", userId);
	}
	
	// ユーザー一覧取得
	public List<Users> getUsers(){
		List<Users> users = userRepository.findAll();
		return users;
	}
	
	public void addQuestion(QuestionForm form) {
		
    	Question question = new Question();

    	copyQuestionForm(question, form);
    	
    	Question savedQuestion = questionRepository.save(question);
    	
    	log.info("問題登録完了 questionId={}", savedQuestion.getQuestionId());
		
	}
	
	public Page<Question> getFilteredQuestions(
	        List<Difficulty> difficulties,
	        String condition,
	        String keyword,
	        Pageable pageable) {

	    // 難易度未選択なら全難易度
	    if (difficulties == null || difficulties.isEmpty()) {
	        difficulties = Arrays.asList(Difficulty.values());
	    }

	    // 条件未選択（「すべて」）ならconditionで絞り込まない
	    boolean includeAllConditions =
	            condition == null || condition.isBlank();

	    // キーワード未入力
	    if (keyword == null) {
	        keyword = "";
	    }

	    return questionRepository.findFilteredAdminQuestionList(
	    		searchConditionConverter.convertDifficulty(difficulties),
	            condition,
	            includeAllConditions,
	            keyword,
	            pageable);
	}
	

	
	public QuestionDto getOneQuestion(long questionId) {

		Question question = questionRepository.findById(questionId)
		        .orElseThrow(() ->
		                new IllegalArgumentException("Question not found."));
		
	    QuestionDto dto = new QuestionDto();

	    dto.setQuestionId(question.getQuestionId());
	    dto.setJapaneseText(question.getJapaneseText());
	    dto.setEnglishText(question.getEnglishText());
	    dto.setAlternativeAnswer(question.getAlternativeAnswer());
	    dto.setDifficulty(question.getDifficulty().name());
	    dto.setCondition(question.getCondition());
		
		return dto;
		
	}
	
	public void updateOneQuestion(long questionId, QuestionForm form) {
		
		Question question = questionRepository.findById(questionId)
		        .orElseThrow(() ->
		                new IllegalArgumentException("Question not found."));
		
		log.info("問題更新前 {}", question);

		copyQuestionForm(question, form);

		questionRepository.save(question);

		log.info("問題更新後 {}", question);
	}
	
	@Transactional
	public void deleteOneQuestion(Long questionId) {

	    favoritesRepository.deleteByQuestionQuestionId(questionId);
	    studyHistoryRepository.deleteByStudyHistoryKeyQuestionId(questionId);
	    questionRepository.deleteById(questionId);

	    log.info("問題削除 questionId={}", questionId);
	}
	
	
	private void copyQuestionForm(Question question, QuestionForm form) {
		question.setJapaneseText(form.getJapaneseText());
		question.setEnglishText(form.getEnglishText());
		question.setAlternativeAnswer(form.getAlternativeAnswer());
		question.setDifficulty(form.getDifficulty());
		question.setCondition(form.getCondition());
	}

		
}