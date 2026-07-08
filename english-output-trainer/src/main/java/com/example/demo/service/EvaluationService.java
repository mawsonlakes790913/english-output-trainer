package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Evaluation;
import com.example.demo.entity.StudyHistory;
import com.example.demo.entity.StudyHistoryKey;
import com.example.demo.entity.Users;
import com.example.demo.repository.StudyHistoryRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EvaluationService {
	
	private final StudyHistoryRepository studyHistoryRepository;
	private final UserServiceImpl userServiceImpl;
	
	public void updateEvaluation(String loginUser, Long questionId, Evaluation evaluation) {
		
		// ユーザー情報を取得
		Users user = userServiceImpl.getUserOne(loginUser);
		
		// 複合キー情報を取得
		StudyHistoryKey key = new StudyHistoryKey();
		key.setUserId(user.getId());
		key.setQuestionId(questionId);
		
		// 存在確認とUPSDATE及びINSERT処理
		Optional<StudyHistory> optionalStudyHistory =
		        studyHistoryRepository.findByStudyHistoryKey(key);
		
		if (optionalStudyHistory.isPresent()) {
			//ここでUPDATE
		    StudyHistory studyHistory = optionalStudyHistory.get();
		    studyHistory.setEvaluation(evaluation);
		    studyHistory.setEvaluationUpdatedAt(LocalDateTime.now());

		    studyHistoryRepository.save(studyHistory);
		    
		} else {
			//ここでUPDATE
		    // INSERT
		    StudyHistory studyHistory = new StudyHistory();
		    studyHistory.setStudyHistoryKey(key);
		    studyHistory.setEvaluation(evaluation);
		    studyHistory.setEvaluationUpdatedAt(LocalDateTime.now());

		    studyHistoryRepository.save(studyHistory);
		}
		
		// JPAはこれでいい
//		public void updateEvaluation(
//		        String loginUser,
//		        Long questionId,
//		        String evaluation) {
//
//		    // ユーザー情報を取得
//		    Users user = userServiceImpl.getUserOne(loginUser);
//
//		    // 複合キーを作成
//		    StudyHistoryKey key = new StudyHistoryKey();
//		    key.setUserId(user.getId());
//		    key.setQuestionId(questionId);
//
//		    // エンティティを作成
//		    StudyHistory studyHistory = new StudyHistory();
//		    studyHistory.setStudyHistoryKey(key);
//		    studyHistory.setEvaluation(evaluation);
//		    studyHistory.setEvaluationUpdatedAt(LocalDateTime.now());
//
//		    // INSERTまたはUPDATE（JPAが自動判定）
//		    studyHistoryRepository.save(studyHistory);
//		}
		
		
	}
	
}
