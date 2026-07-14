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
	
}