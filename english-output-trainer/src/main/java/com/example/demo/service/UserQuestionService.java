package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserQuestionListDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.FavoriteCondition;
import com.example.demo.entity.StudyCondition;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.util.SearchConditionConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQuestionService {
	
	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	private final SearchConditionConverter searchConditionConverter;

	public Page<UserQuestionListDto> getFilteredUserQuestionList(long userId,
						 List<Difficulty> difficulties,
						 List<Evaluation> evaluations,
						 StudyCondition studyCondition,
						 FavoriteCondition favoriteCondition,
						 List<String> conditions,
						 String keyword,																 
						 Pageable pageable) {
	
		// 難易度
		if (difficulties == null || difficulties.isEmpty()) {
		difficulties = Arrays.asList(Difficulty.values());
		}
		
		// 理解度
		if (evaluations == null || evaluations.isEmpty()) {
		evaluations = Arrays.asList(Evaluation.values());
		}
		
		// 学習条件
		if (studyCondition == null) {
		studyCondition = StudyCondition.ALL;
		}
		
		// お気に入り条件
		if (favoriteCondition == null) {
		favoriteCondition = FavoriteCondition.ALL;
		}
		
		// 条件
		boolean includeAllConditions = (conditions == null || conditions.isEmpty());

		if (includeAllConditions) {
		    // IN句が空にならないようダミーを設定
		    conditions = List.of("");
		}
		
		// キーワード
		if (keyword == null) {
		keyword = "";
		}
		
		// ここで変換する
		List<String> convertedDifficulties = searchConditionConverter.convertDifficulty(difficulties);
		List<String> convertedEvaluations = searchConditionConverter.convertEvaluation(evaluations);
		String convertedStudyCondition = searchConditionConverter.convertStudyCondition(studyCondition);
		String convertedFavoriteCondition = searchConditionConverter.convertFavoriteCondition(favoriteCondition);
		
		return questionRepository.findFilteredUserQuestionList(
		userId,
		convertedDifficulties,
		convertedEvaluations,
		convertedStudyCondition,
		convertedFavoriteCondition,
		includeAllConditions,
		conditions,
		keyword,
		pageable);
	}	
	
}