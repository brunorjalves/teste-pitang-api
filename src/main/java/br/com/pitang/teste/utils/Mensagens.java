package br.com.pitang.teste.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Mensagens {

	private @Autowired MessageSource messageSource;
	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource);
	}

	public String get(String code) {
		return accessor.getMessage(code);
	}

}