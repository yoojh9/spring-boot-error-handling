package yjh.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Book {

	@NotEmpty(message="title can't be empty")
	private String title;
	
	@NotEmpty(message="keyword can't be empty")
	private String keyword;
	
	@NotNull
	private Integer price;
	
	
	
}
