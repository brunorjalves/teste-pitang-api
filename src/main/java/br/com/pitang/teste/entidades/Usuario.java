package br.com.pitang.teste.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "tb_user")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "birthday")
	private LocalDate birthday;

	@NotEmpty
	@Column(name = "login")
	private String login;

	@JsonInclude(value = Include.NON_NULL)
	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotEmpty
	@Column(name = "phone")
	private String phone;

	@JsonInclude(value = Include.NON_NULL)
	@NotNull
	@Column(name = "created_at")
	private LocalDate createdAt;

	@JsonInclude(value = Include.NON_NULL)
	@Column(name = "last_login")
	private LocalDate lastLogin;

	@JsonInclude(value = Include.NON_EMPTY)
	@Cascade(CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_car", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_car"))
	private List<Carro> cars;

}
