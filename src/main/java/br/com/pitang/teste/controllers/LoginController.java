package br.com.pitang.teste.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.teste.utils.Mensagens;

@RestController
public class LoginController {

	@Autowired
	protected Mensagens mensagens;

	@GetMapping("/signin")
	public ResponseEntity<String> signin() {
		return ResponseEntity.ok("Tentando logar");
	}

}
