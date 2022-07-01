package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;

//As classes DTO controlam quais campos quero devolver
public class TopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	//Converte uma lista de Topico para uma lista de TopicoDto
	public static List<TopicoDto> converter(List<Topico> topicos) {
		/**
		 * map -> Faz a conversao.
		 * TopicoDto::new -> Faz o foreach. Para cada Topico cria uma instância de TopicoDto.
		 * Para cada iteração chama o construtor que recebe um objeto Topico por parâmetro. 
		 * Collectors.toList() -> Transforma em uma lista.
		 */
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}

}
