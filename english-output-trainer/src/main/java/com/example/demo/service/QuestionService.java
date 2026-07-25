package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<String> getAllConditions() {
	    return questionRepository.findDistinctConditions();
	}
}