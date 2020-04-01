package br.com.pitang.util;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pitang.teste.entidades.Carro;
import br.com.pitang.teste.entidades.Usuario;

public class TestUtils {

	public static String toJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Usuario getUsuarioCompleto() {
		return Usuario.builder() //
				.firstName("John") //
				.lastName("Walker") //
				.login("john.walker") //
				.password("1234") //
				.email("john.walker@email.com") //
				.phone("81999998888") //
				.birthday(LocalDate.now()) //
				.createdAt(LocalDate.now()) //
				.build();
	}

	public static Carro getCarroCompleto() {
		return Carro.builder() //
				.model("Kwid") //
				.licensePlate("KOP2525") //
				.color("white") //
				.year(2020) //
				.build();
	}

}
