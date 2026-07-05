package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudyHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final QuestionRepository questionRepository;
	private final StudyHistoryRepository studyHistoryRepository;
	
//	public long countTotalAdvanced() {
//		long totalAdvanced = questionRepository.countByDifficulty("Advanced");
//		return totalAdvanced;
//	}
//	
//	public long countTotalIntermediate() {
//		long totalIntermediate = questionRepository.countByDifficulty("Intermediate");
//		return totalIntermediate;
//	}
//
//	public long countTotalBegginer() {
//		long totalBegginner = questionRepository.countByDifficulty("Beginner");
//		return totalBeginner;
//	}
	
	
	public long countEvaluatedHard(Long userId) {
        return studyHistoryRepository
                .countByStudyHistoryKeyUserIdAndEvaluation(
                        userId,
                        "HARD");
    }
	
	public long countEvaluatedGood(Long userId) {
        return studyHistoryRepository
                .countByStudyHistoryKeyUserIdAndEvaluation(
                        userId,
                        "GOOD");
    }
	
	public long countEvaluatedEasy(Long userId) {
        return studyHistoryRepository
                .countByStudyHistoryKeyUserIdAndEvaluation(
                        userId,
                        "EASY");
    }

//
//	int getFavoriteCount(String userId);
//
//	int getHardGoodCount(String userId);
}