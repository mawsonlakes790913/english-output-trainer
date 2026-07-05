package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.StudyHistory;
import com.example.demo.entity.StudyHistoryKey;

public interface StudyHistoryRepository extends JpaRepository<StudyHistory, StudyHistoryKey> {
	
	Optional<StudyHistory> findByStudyHistoryKey(StudyHistoryKey studyHistoryKey);
	
	long countByStudyHistoryKeyUserIdAndEvaluation(
	        Long userId,
	        String evaluation);
}