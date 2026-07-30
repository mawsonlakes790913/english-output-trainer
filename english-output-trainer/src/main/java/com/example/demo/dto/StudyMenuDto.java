package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class StudyMenuDto {
	
    private long beginnerCount;
    private List<Range> beginnerRanges;
    private long intermediateCount;
    private List<Range> intermediateRanges;
    private long advancedCount;
    private List<Range> advancedRanges;
    
}