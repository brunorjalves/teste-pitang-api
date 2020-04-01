package br.com.pitang.teste.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.teste.config.security.jwt.JwtRequest;
import br.com.pitang.teste.config.security.jwt.JwtResponse;
import br.com.pitang.teste.config.security.jwt.JwtToken;
import br.com.pitang.teste.services.JwtUserDetailsService;
import br.com.pitang.teste.services.UsuarioService;

@RestController
@RequestMapping("/signin")
public class LoginController {

	protected @Autowired AuthenticationManager authenticationManager;
	protected @Autowired JwtToken jwtToken;
	protected @Autowired JwtUserDetailsService jwtUserDetailsService;
	protected @Autowired UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest usuario) throws Exception {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword()));
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(usuario.getLogin());
		final String token = jwtToken.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

}
