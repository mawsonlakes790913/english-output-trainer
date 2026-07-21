package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PaginationDto;
import com.example.demo.entity.Difficulty;
import com.example.demo.entity.Question;
import com.example.demo.form.QuestionForm;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;
	private final ReviewService reviewService;
	
	@Transactional
	public void deleteOneUser(String userId) {
		userRepository.deleteByUserId(userId);
	}
	
	public void addQuestion(QuestionForm form) {
		
    	Question question = new Question();

    	question.setJapaneseText(form.getJapaneseText());
    	question.setEnglishText(form.getEnglishText());
    	question.setAlternativeAnswer(form.getAlternativeAnswer());
    	question.setCondition(form.getCondition());
    	question.setDifficulty(form.getDifficulty());
    	
    	Question savedQuestion = questionRepository.save(question);
    	
    	log.info("問題登録完了 questionId={}", savedQuestion.getQuestionId());
		
	}
	
	// 問題一覧取得(全件)
	public Page<Question> getAllQuestions(Pageable pageable) {
		Page<Question> questionList = questionRepository.findAllByOrderByQuestionIdDesc(pageable);
	    return questionList;
	}
	
	public PaginationDto createPagination(Page<?> page) {

	    // 現在のページ番号(0始まり)
	    int currentPage = page.getNumber();

	    // ページ番号の最小値・最大値
	    int startPage = 0;
	    int endPage = page.getTotalPages() - 1;

	    // 現在ページの前後2ページを表示範囲とする
	    int displayStartPage =
	            Math.max(startPage, currentPage - 2);

	    int displayEndPage =
	            Math.min(endPage, currentPage + 2);

	    // 表示ページ数が5ページ未満の場合は不足分を補う
	    int shortage = 0;

	    // 先頭側に寄っている場合は右側へ表示範囲を広げる
	    if (displayStartPage == startPage) {

	        shortage = 4 - (displayEndPage - displayStartPage);

	        displayEndPage =
	                Math.min(endPage,
	                        displayEndPage + shortage);

	    // 末尾側に寄っている場合は左側へ表示範囲を広げる
	    } else if (displayEndPage == endPage) {

	        shortage = 4 - (displayEndPage - displayStartPage);

	        displayStartPage =
	                Math.max(startPage,
	                        displayStartPage - shortage);
	    }

	    // 先頭・末尾の省略記号(...)を表示するか判定
	    boolean showFirstEllipsis =
	            displayStartPage >= 3;

	    boolean showLastEllipsis =
	            displayEndPage <= endPage - 3;

	    // ページネーション情報をDTOへ格納
	    PaginationDto pagination = new PaginationDto();

	    pagination.setCurrentPage(currentPage);
	    pagination.setDisplayStartPage(displayStartPage);
	    pagination.setDisplayEndPage(displayEndPage);
	    pagination.setShowFirstEllipsis(showFirstEllipsis);
	    pagination.setShowLastEllipsis(showLastEllipsis);

	    return pagination;
	}
	
	public List<Question> getFilteredQuestions(
	        List<Difficulty> difficulties,
	        List<String> conditions,
	        String keyword) {

	    if (conditions == null || conditions.isEmpty()) {
	        conditions = getAllConditions();
	    }

	    return questionRepository.findFilteredQuestions(
	            reviewService.convertDifficulty(difficulties),
	            conditions,
	            keyword);
	}
	
	public List<String> getAllConditions() {
	    return questionRepository.findDistinctConditions();
	}
		
}