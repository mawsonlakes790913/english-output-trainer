package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.FavoriteCondition;
import com.example.demo.entity.Question;
import com.example.demo.repository.StudyHistoryRepository;
import com.example.demo.util.SearchConditionConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
//	private final QuestionRepository questionRepository;
	private final StudyHistoryRepository studyHistoryRepository;
	private final SearchConditionConverter searchConditionConverter;
	
//	public long countEvaluation(Long userId, Evaluation evaluation) {
//        return studyHistoryRepository
//                .countByStudyHistoryKeyUserIdAndEvaluation(
//                        userId,
//                        evaluation);
//    }
	
	//復習出題数取得
	public long countReviewQuestions(Long userId, List<Evaluation> evaluations, 
												  List<Difficulty> difficulties,
												  FavoriteCondition favoriteCondition) {
		
	    return studyHistoryRepository.countQuestions(
	            userId,
	            searchConditionConverter.convertEvaluation(evaluations),
	            searchConditionConverter.convertDifficulty(difficulties),
	            searchConditionConverter.convertFavoriteCondition(favoriteCondition));
	}
	

	//問題取得
	public List<Question> getQuestion(Long userId, 
									  List<Evaluation> evaluations, 
									  List<Difficulty> difficulties,
									  FavoriteCondition favoriteCondition,
									  boolean random){
		List<Question> extractedQuestions = studyHistoryRepository.getQuestions(userId,
				searchConditionConverter.convertEvaluation(evaluations),
				searchConditionConverter.convertDifficulty(difficulties),
				searchConditionConverter.convertFavoriteCondition(favoriteCondition));
		
		// シャッフルする
		if (random) {
			Collections.shuffle(extractedQuestions);
		} 
		
		return extractedQuestions;
	}
	
}