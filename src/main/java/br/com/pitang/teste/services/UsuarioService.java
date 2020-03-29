package br.com.pitang.teste.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.repositories.UsuarioRepository;
import br.com.pitang.teste.utils.ConstantesMensagens;
import br.com.pitang.teste.utils.Mensagens;
import br.com.pitang.teste.utils.Utils;

@Service
public class UsuarioService {

	protected @Autowired UsuarioRepository usuarioRepository;
	protected @Autowired Mensagens mensagens;

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario cadastrar(Usuario usuario) throws NegocioException {
		verificaAntesDeSalvar(usuario);
		usuario.setPassword(Utils.critografarSenha(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	public Usuario atualizar(Long id, Usuario usuario) throws NegocioException {
		verificaAntesDeSalvar(usuario);
		Usuario usuarioSalvo = buscarPeloId(id);
		BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
		return usuarioRepository.save(usuarioSalvo);
	}

	public Usuario buscarPeloId(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.isPresent() ? usuario.get() : null;
	}

	public Usuario buscarPeloLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

	public void excluir(Long id) {
		Usuario usuario = buscarPeloId(id);
		usuarioRepository.delete(usuario);
	}

	private void verificaAntesDeSalvar(Usuario usuario) throws NegocioException {
		verificaEmail(usuario.getEmail(), usuario.getId());
		verificaLogin(usuario.getLogin(), usuario.getId());
	}

	private void verificaLogin(String login, Long id) throws NegocioException {
		Usuario usuario = null;

		if (Objects.nonNull(id)) {
			usuario = usuarioRepository.findByLoginAndIdNot(login, id);
		} else {
			usuario = usuarioRepository.findByLogin(login);
		}

		if (Objects.nonNull(usuario)) {
			throw new NegocioException(mensagens.get(ConstantesMensagens.LOGIN_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
		}
	}

	private void verificaEmail(String email, Long id) throws NegocioException {
		Usuario usuario = null;

		if (Objects.nonNull(id)) {
			usuario = usuarioRepository.findByEmailAndIdNot(email, id);
		} else {
			usuario = usuarioRepository.findByEmail(email);
		}

		if (Objects.nonNull(usuario)) {
			throw new NegocioException(mensagens.get(ConstantesMensagens.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
		}
	}

	public void atualizarUltimoLogin(Usuario usuario) {
		usuario.setLastLogin(LocalDate.now());
		usuarioRepository.save(usuario);
	}

}
