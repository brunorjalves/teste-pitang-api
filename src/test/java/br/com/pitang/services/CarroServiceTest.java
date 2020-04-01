package br.com.pitang.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import br.com.pitang.teste.entidades.Carro;
import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.services.CarroService;
import br.com.pitang.teste.services.UsuarioService;
import br.com.pitang.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TesteApplication.class })
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CarroServiceTest {

	protected @Autowired UsuarioService usuarioService;
	protected @Autowired CarroService carroService;
	protected Usuario usuarioLogado;

	@BeforeEach
	public void cadastrarUsuario() throws Exception {
		if (Objects.isNull(usuarioLogado)) {
			Usuario usuario = TestUtils.getUsuarioCompleto();
			usuarioLogado = usuarioService.buscarPeloLogin(usuario.getLogin());

			if (Objects.isNull(usuarioLogado)) {
				usuarioLogado = usuarioService.cadastrar(usuario);
			}
		}
	}

	@Test
	@Order(1)
	public void listarCarrosDoUsuarioLogadoSemCarros() throws Exception {
		List<Carro> carros = carroService.listarTodos();
		assertTrue(carros.isEmpty());
	}

	@Test
	@Order(2)
	public void cadastrarCarroParaOUsuarioLogado() throws Exception {
		Carro carro = TestUtils.getCarroCompleto();
		Carro carroSalvo = carroService.cadastrar(usuarioLogado, carro);
		assertEquals(carro.getLicensePlate(), carroSalvo.getLicensePlate());
	}

	@Test
	@Order(3)
	public void listarCarrosDoUsuarioLogadoComCarros() throws Exception {
		List<Carro> carros = carroService.listarTodos();
		assertFalse(carros.isEmpty());
	}

	@Test
	@Order(4)
	public void cadastrarCarroComMesmaPlaca() throws Exception {
		Assertions.assertThrows(NegocioException.class, () -> {
			carroService.cadastrar(usuarioLogado, TestUtils.getCarroCompleto());
		});
	}

	@Test
	@Order(5)
	public void alterarCarroDoUsuarioLogado() throws Exception {
		Carro carro = carroService.listarTodos().get(0);
		carro.setYear(2019);
		Carro carroAtualizado = carroService.atualizar(usuarioLogado, carro.getId(), carro);
		assertEquals(carro.getYear(), carroAtualizado.getYear());
	}

}
