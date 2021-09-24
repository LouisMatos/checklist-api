package br.com.luismatos.checklistresourceserver.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request)
			throws Exception {

		log.error("Um erro ocorrou ao chamar a api: {}", ex);

		return new ResponseEntity<>(
				new ExceptionalResponde(LocalDate.now(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY),
				HttpStatus.UNPROCESSABLE_ENTITY);

	}

}
