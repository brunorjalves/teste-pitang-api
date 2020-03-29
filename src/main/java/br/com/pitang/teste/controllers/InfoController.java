package br.com.pitang.teste.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.services.UsuarioService;

@RestController
@RequestMapping("/me")
public class InfoController {

	protected @Autowired UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<Usuario> info(Principal principal) {
		Usuario usuario = usuarioService.buscarPeloLogin(principal.getName());
		usuario.setPassword(null);
		return ResponseEntity.ok(usuario);
	}

}
