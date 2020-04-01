package br.com.pitang.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pitang.teste.TesteApplication;
import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.services.UsuarioService;
import br.com.pitang.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TesteApplication.class })
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UsuarioServiceTest {

	protected @Autowired UsuarioService usuarioService;

	@Test
	@Order(1)
	public void listarTodosOsUsuariosSemDados() throws Exception {
		List<Usuario> usuarios = usuarioService.listarTodos();
		assertFalse(usuarios.isEmpty());
	}

	@Test
	@Order(2)
	public void cadastrarNovoUsuario() throws Exception {
		Usuario usuario = TestUtils.getUsuarioCompleto();
		Usuario usuarioCadastrado = usuarioService.cadastrar(usuario);
		assertNotNull(usuarioCadastrado.getId());
	}

	@Test
	@Order(3)
	public void inserirUsuarioComLoginJaCadastrado() throws Exception {
		Assertions.assertThrows(NegocioException.class, () -> {
			Usuario usuario = TestUtils.getUsuarioCompleto();
			usuarioService.cadastrar(usuario);
		});
	}

	@Test
	@Order(4)
	public void inserirUsuarioComEmailJaCadastrado() throws Exception {
		Assertions.assertThrows(NegocioException.class, () -> {
			Usuario usuario = TestUtils.getUsuarioCompleto();
			usuarioService.cadastrar(usuario);
		});
	}

	@Test
	@Order(5)
	public void alterarUsuarioCadastrado() throws Exception {
		Usuario usuarioCadastrado = usuarioService.buscarPeloLogin(TestUtils.getUsuarioCompleto().getLogin());
		usuarioCadastrado.setEmail("novo@email.com");

		Usuario usuarioAtualizado = usuarioService.atualizar(usuarioCadastrado.getId(), usuarioCadastrado);
		assertEquals(usuarioCadastrado.getEmail(), usuarioAtualizado.getEmail());
	}

	@Test
	@Order(6)
	public void listarUsuariosCadastrados() throws Exception {
		List<Usuario> usuarios = usuarioService.listarTodos();
		assertEquals(1, usuarios.size());
	}

	@Test
	@Order(7)
	public void excluirUsuarioCadastrado() throws Exception {
		Usuario usuario = usuarioService.buscarPeloLogin(TestUtils.getUsuarioCompleto().getLogin());
		usuarioService.excluir(usuario.getId());

		Usuario usuarioExcluido = usuarioService.buscarPeloLogin(TestUtils.getUsuarioCompleto().getLogin());
		assertNull(usuarioExcluido);
	}

}
