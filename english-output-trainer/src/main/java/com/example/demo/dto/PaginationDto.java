package com.example.demo.dto;

import lombok.Data;

@Data
public class PaginationDto {

	    private int currentPage;

	    private int displayStartPage;

	    private int displayEndPage;

	    private boolean showFirstEllipsis;

	    private boolean showLastEllipsis;
}