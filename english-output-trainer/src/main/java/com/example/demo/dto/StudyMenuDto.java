package com.example.demo.dto;

import java.util.List;

public class StudyMenuDto {
	
    private long beginnerCount;
    private List<Range> beginnerRanges;
    private long intermediateCount;
    private List<Range> intermediateRanges;
    private long advancedCount;
    private List<Range> advancedRanges;

    
    
	public List<Range> getBeginnerRanges() {
		return beginnerRanges;
	}
	public void setBeginnerRanges(List<Range> beginnerRanges) {
		this.beginnerRanges = beginnerRanges;
	}
	public List<Range> getIntermediateRanges() {
		return intermediateRanges;
	}
	public void setIntermediateRanges(List<Range> intermediateRanges) {
		this.intermediateRanges = intermediateRanges;
	}
	public List<Range> getAdvancedRanges() {
		return advancedRanges;
	}
	public void setAdvancedRanges(List<Range> advancedRanges) {
		this.advancedRanges = advancedRanges;
	}
	public long getBeginnerCount() {
		return beginnerCount;
	}
	public void setBeginnerCount(long beginnerCount) {
		this.beginnerCount = beginnerCount;
	}
	public long getIntermediateCount() {
		return intermediateCount;
	}
	public void setIntermediateCount(long intermediateCount) {
		this.intermediateCount = intermediateCount;
	}
	public long getAdvancedCount() {
		return advancedCount;
	}
	public void setAdvancedCount(long advancedCount) {
		this.advancedCount = advancedCount;
	}
}