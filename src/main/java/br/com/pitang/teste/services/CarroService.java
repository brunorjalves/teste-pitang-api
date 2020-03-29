package br.com.pitang.teste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.pitang.teste.entidades.Carro;
import br.com.pitang.teste.entidades.Usuario;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.repositories.CarroRepository;
import br.com.pitang.teste.utils.ConstantesMensagens;
import br.com.pitang.teste.utils.Mensagens;

@Service
public class CarroService {

	protected @Autowired CarroRepository carroRepository;
	protected @Autowired UsuarioService usuarioService;
	protected @Autowired Mensagens mensagens;

	public List<Carro> listarTodos() {
		return carroRepository.findAll();
	}

	public List<Carro> listarTodosVinculadosAoUsuario(Long idUsuario) {
		Usuario usuario = usuarioService.buscarPeloId(idUsuario);
		List<Carro> carros = null;

		if (Objects.nonNull(usuario)) {
			carros = usuario.getCars();
		}

		return carros;
	}

	public Carro cadastrar(Usuario usuario, Carro carro) throws NegocioException {
		verificaAntesDeSalvar(carro);

		if (CollectionUtils.isEmpty(usuario.getCars())) {
			usuario.setCars(new ArrayList<Carro>());
		}

		usuario.getCars().add(carro);
		Usuario usuarioSalvo = usuarioService.atualizar(usuario.getId(), usuario);

		return usuarioSalvo.getCars().stream().filter(car -> car.getLicensePlate().equals(carro.getLicensePlate()))
				.findFirst().get();
	}

	public Carro atualizar(Usuario usuario, Long id, Carro carro) throws NegocioException {
		Carro carroSalvo = buscarPeloId(usuario, id);

		if (Objects.nonNull(carroSalvo)) {
			verificaAntesDeSalvar(carro);
			BeanUtils.copyProperties(carro, carroSalvo, "id");
			return carroRepository.save(carroSalvo);
		}

		return null;
	}

	public Carro buscarPeloId(Usuario usuario, Long id) {
		Optional<Carro> carro = carroRepository.findById(id);
		return carro.isPresent() && carroPertenceAoUsuarioLogado(carro.get(), usuario) ? carro.get() : null;
	}

	private boolean carroPertenceAoUsuarioLogado(Carro carro, Usuario usuario) {
		return usuario.getCars().contains(carro);
	}

	public void excluir(Usuario usuario, Long id) throws NegocioException {
		Carro carro = buscarPeloId(usuario, id);

		if (Objects.nonNull(carro)) {
			usuario.getCars().remove(carro);
			usuarioService.atualizar(usuario.getId(), usuario);
		}
	}

	private void verificaAntesDeSalvar(Carro carro) throws NegocioException {
		verificaPlaca(carro.getLicensePlate(), carro.getId());
	}

	private void verificaPlaca(String placa, Long id) throws NegocioException {
		Carro carro = null;

		if (Objects.nonNull(id)) {
			carro = carroRepository.findByLicensePlateAndIdNot(placa, id);
		} else {
			carro = carroRepository.findByLicensePlate(placa);
		}

		if (Objects.nonNull(carro)) {
			throw new NegocioException(mensagens.get(ConstantesMensagens.LICENSE_PLATE_ALREADY_EXISTS),
					HttpStatus.BAD_REQUEST);
		}
	}

}
