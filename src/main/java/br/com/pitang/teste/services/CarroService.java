package br.com.pitang.teste.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pitang.teste.entidades.Carro;
import br.com.pitang.teste.exceptions.NegocioException;
import br.com.pitang.teste.repositories.CarroRepository;
import br.com.pitang.teste.utils.Mensagens;

@Service
public class CarroService {

	@Autowired
	protected CarroRepository carroRepository;

	@Autowired
	protected Mensagens mensagens;

	public List<Carro> listarTodos() {
		return carroRepository.findAll();
	}

	// TODO
	public List<Carro> listarTodosVinculadosAoUsuario(Long idUsuario) {
		return null;
	}

	public Carro cadastrarOuAtualizar(Carro carro) throws NegocioException {
		verificaAntesDeSalvar(carro);
		return carroRepository.save(carro);
	}

	public Carro atualizar(Long id, Carro carro) throws NegocioException {
		verificaAntesDeSalvar(carro);
		Carro carroSalvo = buscarPeloId(id);
		BeanUtils.copyProperties(carro, carroSalvo, "id");
		return carroRepository.save(carroSalvo);
	}

	public Carro buscarPeloId(Long id) {
		Optional<Carro> carro = carroRepository.findById(id);
		return carro.isPresent() ? carro.get() : null;
	}

	public void excluir(Long id) {
		Carro carro = buscarPeloId(id);
		carroRepository.delete(carro);
	}

	private void verificaAntesDeSalvar(Carro carro) throws NegocioException {
		verificaPlaca(carro.getLicensePlate(), carro.getId());
	}

	private void verificaPlaca(String placa, Long id) throws NegocioException {

	}

}
