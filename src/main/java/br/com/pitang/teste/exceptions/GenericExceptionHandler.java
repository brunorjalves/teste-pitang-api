package br.com.pitang.teste.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.pitang.teste.utils.ConstantesMensagens;
import br.com.pitang.teste.utils.Mensagens;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	protected @Autowired Mensagens mensagens;

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

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new Erro(mensagens.get(ConstantesMensagens.INVALID_LOGIN_OR_PASSWORD), HttpStatus.UNAUTHORIZED.value()),
				new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new Erro(mensagens.get(ConstantesMensagens.UNAUTHORIZED), HttpStatus.UNAUTHORIZED.value()),
				new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler({ ExpiredJwtException.class })
	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new Erro(mensagens.get(ConstantesMensagens.INVALID_SESSION), HttpStatus.UNAUTHORIZED.value()),
				new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<Object> handleSystemException(NegocioException ex, WebRequest request) {
		return handleExceptionInternal(ex, new Erro(ex.getMessage(), ex.getHttpStatus().value()), new HttpHeaders(),
				ex.getHttpStatus(), request);
	}

}
