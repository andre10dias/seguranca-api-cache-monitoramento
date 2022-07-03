package br.com.alura.forum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/*
 * GrantedAuthority - Indica para o spring security que esta é a entidade que 
 * representa o perfil
 * */
@Entity
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome; //nome do perfil
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	// Método do GrantedAuthority --------------

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return nome;
	}
	
	// ----------------------------------------
	

}
