package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Question;
import com.example.demo.repository.StudyHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
//	private final QuestionRepository questionRepository;
	private final StudyHistoryRepository studyHistoryRepository;
	
	public long countEvaluation(Long userId, Evaluation evaluation) {
        return studyHistoryRepository
                .countByStudyHistoryKeyUserIdAndEvaluation(
                        userId,
                        evaluation);
    }
	
	//復習出題数取得
	public long countReviewQuestions(Long userId, List<Evaluation> evaluations, List<Difficulty> difficulties) {
		
	    return studyHistoryRepository.countQuestions(
	            userId,
	            convertEvaluation(evaluations),
	            convertDifficulty(difficulties));
	}

	//問題取得
	public List<Question> getQuestion(Long userId, 
									  List<Evaluation> evaluations, 
									  List<Difficulty> difficulties,
									  boolean random){
		List<Question> extractedQuestions = studyHistoryRepository.getQuestions(userId,
				convertEvaluation(evaluations),
				convertDifficulty(difficulties));
		
		// シャッフルする
		if (random) {
			Collections.shuffle(extractedQuestions);
		} 
		
		return extractedQuestions;
	}
	
	//List<Evaluation>をList<String>に変換
	public List<String> convertEvaluation(List<Evaluation> evaluations) {
		
		List<String> evaluationList;
		
		if (evaluations == null || evaluations.isEmpty()) {

	        evaluationList = List.of(
	                Evaluation.HARD.name(),
	                Evaluation.GOOD.name(),
	                Evaluation.EASY.name());

	    } else {

	        evaluationList = new ArrayList<>();

	        for (Evaluation evaluation : evaluations) {
	            evaluationList.add(evaluation.name());
	        }
	   
	    }
	        
		return evaluationList;
		
	}
	
	
	//List<Difficulty>をList<String>に変換
	public List<String> convertDifficulty(List<Difficulty> difficulties) {

		List<String> difficultyList;
		
	    // 難易度
	    if (difficulties == null || difficulties.isEmpty()) {

	        difficultyList = List.of(
	                Difficulty.BEGINNER.name(),
	                Difficulty.INTERMEDIATE.name(),
	                Difficulty.ADVANCED.name());

	    } else {

	        difficultyList = new ArrayList<>();

	        for (Difficulty difficulty : difficulties) {
	            difficultyList.add(difficulty.name());
	        }
	    }
	    
	    return difficultyList;
	    
	}
	

//
//	int getFavoriteCount(String userId);
//
//	int getHardGoodCount(String userId);
}