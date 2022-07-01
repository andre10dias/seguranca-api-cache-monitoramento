package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

/*
 *  - Padrão Repository
 *  - Substitui as classes padrão DAO utilizados em projetos tradicionais em java 
 *  com os métodos de CRUD (listagem, edição, inclusão e exclusão)
 *  - Interface que herda da interface JpaRepository recebendo a entidade com que 
 * 	ela vai trabalhar e o tipo de dado do atributo id desta entidade 
 * */
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);
	
	//@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	//List<Topico> carregaPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}

/*
 * Caso utilize o padrão de nomeclatura do spring data (findBy[nomeDoAtributoDaEntidade]), 
 * ele consegue gerar a query automaticamente.
 * 
 * Exemplo:
 * findByTiulo -> Faz uma busca na entidade 'Topico' através do atributo 'titulo'.
 * 
 * E para filtrar por um atributo do relacionamento de uma entidade?
 * (findBy[nomeDoRelacionamento + nomeDoAtributoDoRelacionamento])
 * 
 * Exemplo:
 * findByCursoNome -> Faz a busca através do atributo 'nome' da entidade 'Curso' que 
 * 					possui relacionamento com a entidade 'Topico'.
 * ou
 * 
 * findByCurso_Nome -> Para evitar erro/problema por ambiguidade, caso a entidade 'Topico' 
 * 					também possua um atributo 'nome'.
 * 
 * Caso não utilize o padrão de nomeclatura do spring data, teria que utilizar o padrão
 * JPQL através da anotação '@Query' e '@Param'.
 * 
 * */
