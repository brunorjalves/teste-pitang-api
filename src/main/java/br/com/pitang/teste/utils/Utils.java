package br.com.pitang.teste.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Utils {

	static PasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String critografarSenha(String senha) {
		return encoder.encode(senha);
	}

	public static boolean senhaValida(String senhaBanco, String senhaInformada) {
		return encoder.matches(senhaBanco, senhaInformada);
	}
	
	public static boolean senhaInvalida(String senhaBanco, String senhaInformada) {
		return !senhaValida(senhaBanco, senhaInformada);
	}

}
