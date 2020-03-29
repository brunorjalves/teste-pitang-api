package br.com.pitang.teste.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pitang.teste.entidades.Usuario;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	protected @Autowired UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.buscarPeloLogin(login);

		if (usuario == null) {
			throw new UsernameNotFoundException("User not found with login: " + login);
		}

		usuarioService.atualizarUltimoLogin(usuario);
		return new User(usuario.getLogin(), usuario.getPassword(), new ArrayList<>());
	}

}
