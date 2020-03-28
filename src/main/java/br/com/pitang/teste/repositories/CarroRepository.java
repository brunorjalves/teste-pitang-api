package br.com.pitang.teste.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pitang.teste.entidades.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long> {

	Carro findByLicensePlate(String licensePlate);

}
