package br.com.pitang.teste.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public NegocioException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public NegocioException(String message) {
		super(message);
	}

}
