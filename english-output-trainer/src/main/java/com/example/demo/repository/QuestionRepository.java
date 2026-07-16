package com.example.demo.repository;

import java.util.List;

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
				    @Param("difficulties") List<String> difficulties					
					);
	
}