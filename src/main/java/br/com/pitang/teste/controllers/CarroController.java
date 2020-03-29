package br.com.pitang.teste.controllers;

import java.security.Principal;
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

import br.com.pitang.teste.entidades.Carro;
import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.services.CarroService;
import br.com.pitang.teste.services.UsuarioService;

@RestController
@RequestMapping("/cars")
public class CarroController {

	protected @Autowired CarroService carroService;
	protected @Autowired UsuarioService usuarioService;

	private Usuario getUsuarioLogado(Principal principal) {
		return usuarioService.buscarPeloLogin(principal.getName());
	}

	@GetMapping
	public ResponseEntity<List<Carro>> listarTodosCarrosDoUsuario(Principal principal) {
		Usuario usuarioLogado = getUsuarioLogado(principal);
		List<Carro> carros = null;

		if (Objects.nonNull(usuarioLogado)) {
			carros = carroService.listarTodosVinculadosAoUsuario(usuarioLogado.getId());
		}

		return CollectionUtils.isEmpty(carros) ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
	}

	@PostMapping
	public ResponseEntity<Carro> cadastrarCarro(@Valid @RequestBody Carro carro, Principal principal)
			throws NegocioException {
		Carro carroSalvo = carroService.cadastrar(getUsuarioLogado(principal), carro);
		return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carro> buscarPeloId(@PathVariable Long id, Principal principal) {
		Carro carro = null;
		Usuario usuarioLogado = getUsuarioLogado(principal);

		if (Objects.nonNull(usuarioLogado)) {
			carro = carroService.buscarPeloId(usuarioLogado, id);
		}

		return Objects.nonNull(carro) ? ResponseEntity.ok(carro) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Carro> atualizar(@Valid @PathVariable Long id, @RequestBody Carro carro, Principal principal)
			throws NegocioException {
		Usuario usuarioLogado = getUsuarioLogado(principal);
		Carro carroSalvo = null;

		if (Objects.nonNull(usuarioLogado)) {
			carroSalvo = carroService.atualizar(usuarioLogado, id, carro);
		}

		return Objects.nonNull(carroSalvo) ? ResponseEntity.ok(carroSalvo)
				: ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id, Principal principal) throws NegocioException {
		Usuario usuarioLogado = getUsuarioLogado(principal);

		if (Objects.nonNull(usuarioLogado)) {
			carroService.excluir(usuarioLogado, id);
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
