package br.com.pitang.teste.controllers;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.services.UsuarioService;

@RestController
@RequestMapping("/users")
public class UsuarioController {

	protected @Autowired UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		return CollectionUtils.isEmpty(usuarios) ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
	}

	@PostMapping
	public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) throws NegocioException {
		Usuario usuarioSalvo = usuarioService.cadastrar(usuario);
		usuarioSalvo.setLastLogin(null);
		usuarioSalvo.setCreatedAt(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPeloId(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscarPeloId(id);
		return Objects.nonNull(usuario) ? ResponseEntity.ok(usuario)
				: ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@Valid @PathVariable Long id, @RequestBody Usuario usuario)
			throws NegocioException {
		Usuario usuarioSalvo = usuarioService.atualizar(id, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		usuarioService.excluir(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
