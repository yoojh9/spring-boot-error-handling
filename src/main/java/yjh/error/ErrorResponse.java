package yjh.error;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data

public class ErrorResponse {

	private HttpStatus status;
	private Integer code;
	private String message;
	private String path;
	
	public ErrorResponse(HttpStatus status, Integer code, String message, String path) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}
	
}
