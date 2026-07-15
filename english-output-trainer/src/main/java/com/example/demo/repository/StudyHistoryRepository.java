package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Question;
import com.example.demo.entity.StudyHistory;
import com.example.demo.entity.StudyHistoryKey;

public interface StudyHistoryRepository extends JpaRepository<StudyHistory, StudyHistoryKey> {
	
	Optional<StudyHistory> findByStudyHistoryKey(StudyHistoryKey studyHistoryKey);
	
	long countByStudyHistoryKeyUserIdAndEvaluation(
	        Long userId,
	        Evaluation evaluation);
	
//	@Query(value = """
//			SELECT COUNT(*)
//			FROM study_history sh
//			JOIN question q
//			  ON sh.question_id = q.question_id
//			WHERE sh.user_id = :userId
//			  AND sh.evaluation IN (:evaluations)
//			  AND q.difficulty IN (:difficulties)
//			""", nativeQuery = true)
//			long countQuestions(
//				    @Param("userId") Long userId,
//				    @Param("evaluations") List<String> evaluations,
//				    @Param("difficulties") List<String> difficulties
//				);
	
	
	@Query(value = """
			SELECT q.*
			FROM study_history sh
			JOIN question q
			  ON sh.question_id = q.question_id
			WHERE sh.user_id = :userId
			  AND sh.evaluation IN (:evaluations)
			  AND q.difficulty IN (:difficulties)
			ORDER BY sh.evaluation_updated_at ASC
			""", nativeQuery = true)
			List<Question> getQuestions(
					    @Param("userId") Long userId,
					    @Param("evaluations") List<String> evaluations,
					    @Param("difficulties") List<String> difficulties
					);
	
	// ======================================
	// ① お気に入り登録された問題のみ
	// ======================================
	@Query(value = """
	        SELECT COUNT(*)
	        FROM study_history sh
	        JOIN question q
	          ON sh.question_id = q.question_id
	        LEFT JOIN favorites f
	          ON sh.user_id = f.user_id
	         AND sh.question_id = f.question_id
	        WHERE sh.user_id = :userId
	          AND sh.evaluation IN (:evaluations)
	          AND q.difficulty IN (:difficulties)
	          AND f.question_id IS NOT NULL
	        """, nativeQuery = true)
	long countFavoritedQuestions(
	        @Param("userId") Long userId,
	        @Param("evaluations") List<String> evaluations,
	        @Param("difficulties") List<String> difficulties
	);
	
	// ======================================
	// ② お気に入り登録されていない問題のみ
	// ======================================
	@Query(value = """
	        SELECT COUNT(*)
	        FROM study_history sh
	        JOIN question q
	          ON sh.question_id = q.question_id
	        LEFT JOIN favorites f
	          ON sh.user_id = f.user_id
	         AND sh.question_id = f.question_id
	        WHERE sh.user_id = :userId
	          AND sh.evaluation IN (:evaluations)
	          AND q.difficulty IN (:difficulties)
	          AND f.question_id IS NULL
	        """, nativeQuery = true)
	long countNonFavoritedQuestions(
	        @Param("userId") Long userId,
	        @Param("evaluations") List<String> evaluations,
	        @Param("difficulties") List<String> difficulties
	);
	
	// ======================================
	// ③ お気に入り登録に関係なく取得
	// ======================================
	@Query(value = """
	        SELECT COUNT(*)
	        FROM study_history sh
	        JOIN question q
	          ON sh.question_id = q.question_id
	        LEFT JOIN favorites f
	          ON sh.user_id = f.user_id
	         AND sh.question_id = f.question_id
	        WHERE sh.user_id = :userId
	          AND sh.evaluation IN (:evaluations)
	          AND q.difficulty IN (:difficulties)
	        """, nativeQuery = true)
	long countQuestions(
	        @Param("userId") Long userId,
	        @Param("evaluations") List<String> evaluations,
	        @Param("difficulties") List<String> difficulties
	);
	
}