package com.example.demo.service;



import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class StudyServiceImpl implements StudyService {
	
	private final QuestionRepository repository;
	
	public List<Question> getRandomQuestion(){
		
		List<Question> extractedQuestions = repository.findAll(); 
		Collections.shuffle(extractedQuestions);
		// シャッフルが行われているかコンソールに出力して確認
		for (Question q : extractedQuestions) {
		    System.out.print(q.getQuestionId() + " ");
		}
		System.out.println();
		return extractedQuestions;
	}
}