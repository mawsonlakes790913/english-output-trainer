package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "study_history")
public class StudyHistory {
	@EmbeddedId
	private StudyHistoryKey studyHistoryKey;
	
    @Enumerated(EnumType.STRING)
    private Evaluation evaluation;
    
	private LocalDateTime evaluationUpdatedAt;
}