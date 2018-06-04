package yjh.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yjh.domain.Book;
import yjh.error.DataNotFoundException;


/**
 * GlobalExceptionHandler 테스트용
 * @author yjh
 *
 */
@RestController
@RequestMapping("/book")
public class BookController {

	// GET /test 
	// type 파라미터를 전달하지 않을 경우 MissingServletRequestParameterException 발생
	@GetMapping("")
	public String main(@RequestParam(name="type") String type){
	  return "hi";
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Book> createLecture(@RequestBody @Valid Book book){
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	// custom exception handling test 
	@GetMapping("/no-data")
	public String test404(){
		throw new DataNotFoundException("lecture data not found");
	}

	// default error handling test
	@GetMapping("/default-error")
	public String defaultErrorHandling(){
		throw new NullPointerException("title can not be null");
	}
	
	@GetMapping("/123")
	@ResponseBody
	public Book responseOk(){
		Book book = new Book();
		book.setTitle("제목");
		book.setKeyword("키워드1,키워드2,키워드3");
		book.setPrice(15000);
		return book;
	}

}
