package br.com.luismatos.checklistresourceserver.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionalResponde {

	private LocalDate timestamp;

	private String message;

	private HttpStatus status;

}
