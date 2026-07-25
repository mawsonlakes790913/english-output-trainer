package com.example.demo.service;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.NewStudyCountDto;
import com.example.demo.dto.Range;
import com.example.demo.dto.StudyMenuDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class StudyService {
	
	private final QuestionRepository questionRepository;
	private final ReviewService reviewService;
	
//	public List<Question> getQuestion(){
//		List<Question> extractedQuestions = questionRepository.findAll();
//		return extractedQuestions;
//	}
	
	public List<Question> getQuestions(Difficulty difficulty,
									  int start,
									  boolean random){
		
		int offset = start - 1;
		
		List<Question> extractedQuestions = questionRepository.getQuestions(
		        difficulty.name(),
		        offset
		);
		
		// シャッフルする
		if (random) {
			Collections.shuffle(extractedQuestions);
		} 
		
		
		return extractedQuestions;
	}
	
//	public List<Question> getRandomQuestion(){
//		
//		List<Question> extractedQuestions = questionRepository.findAll(); 
//		Collections.shuffle(extractedQuestions);
//		// シャッフルが行われているかコンソールに出力して確認
//		for (Question q : extractedQuestions) {
//		    System.out.print(q.getQuestionId() + " ");
//		}
//		System.out.println();
//		return extractedQuestions;
//	}
	
	public StudyMenuDto countStudyQuestions() {
		
		StudyMenuDto count = new StudyMenuDto();
	    
	    long beginnerCount = questionRepository.countByDifficulty(Difficulty.BEGINNER);
	    count.setBeginnerCount(beginnerCount);
	    
	    List<Range> beginnerRanges = createRanges(beginnerCount);
	    count.setBeginnerRanges(beginnerRanges);
	    
	    
	    long intermediateCount = questionRepository.countByDifficulty(Difficulty.INTERMEDIATE);
	    count.setIntermediateCount(intermediateCount);
	    
	    List<Range> intermediateRanges = createRanges(intermediateCount);
	    count.setIntermediateRanges(intermediateRanges);

	    long advancedCount = questionRepository.countByDifficulty(Difficulty.ADVANCED);
	    count.setAdvancedCount(advancedCount);
	    
	    List<Range> advancedRanges = createRanges(advancedCount);
	    count.setAdvancedRanges(advancedRanges);
	    
	    return count;


	}
	
	public NewStudyCountDto countNewStudyQuestions(Long userId) {
		
		NewStudyCountDto count = new NewStudyCountDto();
		
	    long beginnerCount = questionRepository.countNewQuestions(userId, Difficulty.BEGINNER.name());
	    count.setBeginnerCount(beginnerCount);	    
	    
	    long intermediateCount = questionRepository.countNewQuestions(userId, Difficulty.INTERMEDIATE.name());
	    count.setIntermediateCount(intermediateCount);

	    long advancedCount = questionRepository.countNewQuestions(userId, Difficulty.ADVANCED.name());
	    count.setAdvancedCount(advancedCount);
		
		return count;
	}
	
	private List<Range> createRanges(long count) {
		List<Range> ranges = new ArrayList<>();

		for (long start = 1; start <= count; start += 100) {

		    if (start + 99 <= count) {
		        ranges.add(new Range(start, start + 99));
		    } else {
		        ranges.add(new Range(start, count));
		    }
		}

		return ranges;
	}
	
	public List<Question> getRandomQuestion(){
		
		List<Question> extractedQuestions = questionRepository.findAll(); 
		Collections.shuffle(extractedQuestions);
		// シャッフルが行われているかコンソールに出力して確認
		for (Question q : extractedQuestions) {
		    System.out.print(q.getQuestionId() + " ");
		}
		System.out.println();
		return extractedQuestions;
	}
	
	public List<Question> getNewQuestions(long userId, List<Difficulty> difficulty) {
		
		List<Question> extractedNewQuestions = questionRepository.getNewQuestions(userId, reviewService.convertDifficulty(difficulty));
		
		return extractedNewQuestions;
	}
}