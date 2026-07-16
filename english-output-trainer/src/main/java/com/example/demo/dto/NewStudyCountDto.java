package com.example.demo.dto;

public class NewStudyCountDto {

    private long beginnerCount;
    private long intermediateCount;
    private long advancedCount;
    
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