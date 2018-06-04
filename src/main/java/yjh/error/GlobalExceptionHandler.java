package yjh.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * DataNotFoundException 커스텀 exception이 발생할 경우
	 * ExceptionHandler({}) 태그는 다음과 같이 사용하여 복수의 exception을 처리할 수도 있음
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DataNotFoundException.class)
	private ResponseEntity<Object> handleDataNotFound(DataNotFoundException ex, WebRequest request){
		String message = ex.getMessage();
		Integer code = ErrorCode.NOT_FOUND.getCode();
		return responseEntityBuilder(HttpStatus.NOT_FOUND, code, message, request);
	}
	
	/**
	 * 속성이 'required=true'인 request paramter가 없을 경우 발생
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = String.format("%s parameter is missing", ex.getParameterName());
		Integer code = ErrorCode.MISSING_PARAMETER.getCode();
		return responseEntityBuilder(HttpStatus.BAD_REQUEST, code, message, request);
	}
	
	/**
	 * 객체가 @Valid 유효성 검증에 실패할 경우 발생
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "validation error";
		Integer code = ErrorCode.INVALID_ARGUMENT.getCode();
		logger.error(ex.getMessage());
		return responseEntityBuilder(HttpStatus.BAD_REQUEST, code, message, request);
	}

	/**
	 * request JSON 형식이 올바르지 않을 경우 발생
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "Malformed JSON request";
		Integer code = ErrorCode.MARFORMED_JSON_REQUEST.getCode();
		logger.error(ex.getMessage());
		return responseEntityBuilder(HttpStatus.BAD_REQUEST, code, message, request);
	}
	
	/**
	 * request method 타입이 지원하지 않는 타입일 때 발생
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		Integer code = ErrorCode.METHOD_NOT_ALLOWED.getCode();
		return responseEntityBuilder(HttpStatus.METHOD_NOT_ALLOWED, code, message, request);
	}

	/**
	 * httpMediaTypeNotSupportedException
	 * RequestBody json 객체를 요청 받는 api에 form data를 보낼 경우에도 발생함
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {	
		String message = String.format("%s media type is not supported", ex.getContentType());
		Integer code = ErrorCode.NOT_ACCEPTABLE.getCode();	
		return responseEntityBuilder(HttpStatus.NOT_ACCEPTABLE, code, message, request);
	}

	/**
	 * default server error handling
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request){
		String message = ex.getMessage();
		Integer code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
		return responseEntityBuilder(HttpStatus.INTERNAL_SERVER_ERROR, code, message, request);
	}
	
	private ResponseEntity<Object> responseEntityBuilder(HttpStatus status, int code, String message, WebRequest request){
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		ErrorResponse errorResponse = new ErrorResponse(status, code, message, path);
		logger.error("error code: {}, message: {},  path: {}",code, message, path);
		return new ResponseEntity<Object>(errorResponse, errorResponse.getStatus());
	}
}
