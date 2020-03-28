package br.com.pitang.teste.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.pitang.teste.utils.ConstantesMensagens;
import br.com.pitang.teste.utils.Mensagens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	protected Mensagens mensagens;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(mensagens.get(ConstantesMensagens.MISSING_FIELDS), 400), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(mensagens.get(ConstantesMensagens.INVALID_FIELDS), 400), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<Object> handleSystemException(NegocioException ex, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(ex.getMessage(), ex.getHttpStatus().value()), new HttpHeaders(),
				ex.getHttpStatus(), request);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public class Erro {
		private String message;
		private Integer errorCode;
	}

}
