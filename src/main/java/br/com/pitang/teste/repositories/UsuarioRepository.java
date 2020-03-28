package br.com.pitang.teste.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pitang.teste.entidades.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	Usuario findByLogin(String login);

	Usuario findByEmailAndIdNot(String email, Long id);

	Usuario findByLoginAndIdNot(String login, Long id);

}
