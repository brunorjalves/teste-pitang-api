package br.com.pitang.teste.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Erro {
	private String message;
	private Integer errorCode;
}
