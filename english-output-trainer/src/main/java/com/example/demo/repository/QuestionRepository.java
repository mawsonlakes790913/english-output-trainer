package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	long countByDifficulty(Difficulty difficulty);
	
	@Query(value = """
			SELECT *
			FROM question
			WHERE difficulty = :difficulty
			ORDER BY question_id
			LIMIT 100 OFFSET :offset
			""", nativeQuery = true)
			List<Question> getQuestions(
					@Param("difficulty") String difficulty,
					@Param("offset") int offset
					);
	
	
	@Query(value = """
			SELECT COUNT(*)
			FROM question q
			LEFT JOIN study_history sh
			  ON q.question_id = sh.question_id
			 AND sh.user_id = :userId
			WHERE q.difficulty IN (:difficulties)
			  AND sh.question_id IS NULL
			""", nativeQuery = true)
			long countNewQuestions(
				    @Param("userId") Long userId,
				    @Param("difficulties") String difficulties					
					);
	
	
	@Query(value = """
			SELECT q.*
			FROM question q
			LEFT JOIN study_history sh
			  ON q.question_id = sh.question_id
			 AND sh.user_id = :userId
			WHERE q.difficulty IN (:difficulties)
			  AND sh.question_id IS NULL
			""", nativeQuery = true)
			List<Question> getNewQuestions(
				    @Param("userId") Long userId,
				    @Param("difficulties") List<String> difficulties					
					);
	
	Page<Question> findAllByOrderByQuestionIdDesc(Pageable pageable);
	
	@Query(value = """
			SELECT DISTINCT q.condition
			FROM Question q
			WHERE q.condition IS NOT NULL
			ORDER BY q.condition
			""")
			List<String> findDistinctConditions();
	
	@Query(value = """
			SELECT q.*
			FROM question q
			WHERE q.difficulty IN (:difficulties)
			AND q.condition IN (:conditions)
			AND 
			(LOWER(q.japanese_text) LIKE LOWER(CONCAT('%', :keyword, '%'))
			OR
			LOWER(q.english_text) LIKE LOWER(CONCAT('%', :keyword, '%'))
			OR
			LOWER(q.alternative_answer) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
			ORDER BY q.question_id DESC
			""", nativeQuery = true)
			Page<Question> findFilteredQuestions(
					@Param("difficulties") List<String> difficulties,
					@Param("conditions") List<String> conditions,
					@Param("keyword") String keyword,
					Pageable pageable);
	
	
}