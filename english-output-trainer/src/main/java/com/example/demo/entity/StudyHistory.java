package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "study_history")
public class StudyHistory {
	@EmbeddedId
	private StudyHistoryKey studyHistoryKey;
	private String evaluation;
	private LocalDateTime evaluationUpdatedAt;
}