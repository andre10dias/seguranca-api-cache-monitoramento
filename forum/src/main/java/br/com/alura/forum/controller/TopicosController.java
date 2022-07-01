package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

/*
 * @RestController -> Elimina a necessidade de usar a anotação @ResponseBody em todos 
 * os métodos, sendo utilizar o @ResponseBody nas requisições onde o parâmetro não 
 * vem na url, mas no corpo da requisição.
 * 
 * @RequestMapping -> Elimina a necessidade de usar o mapeamento em todos os métodos, 
 * passando a utilizar apenas a anotação correspondente ao verbo http.
 * 
 * */
@RestController 
@RequestMapping("/topicos") 
public class TopicosController {
	
	//Injeção de dependência (interface TopicoRepository)
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	//@RequestMapping(value = "/topicos", method = RequestMethod.GET)
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		//Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		
		//Arrays.asList - Recebe vários objetos e retorna uma lista com esses objetos
		//return TopicoDto.converter(Arrays.asList(topico, topico, topico));
		
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
		
	}
	
	//@RequestMapping(value = "/topicos", method = RequestMethod.POST)
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	//public TopicoDto detalhar(@PathVariable("id") Long codigo) {
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		//getOne e getById foram depreciados
		//Topico topico = topicoRepository.getReferenceById(id);
		
		//Optional -> Pode ser que tenha o registro pode ser que não tenha
		//Elimina o erro caso o parâmetro passado não exista
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	

}

/*
 * @PathVariable vs @RequestParam
 * 
 * Geralmente RequestParam é utilizado em consultas, para passar os parâmetros de filtros e paginação. 
 * Ex:
 * /livros?autor=Joao
 * /livros/busca?anoPublicacao=2010&size=10&page=2
 * 
 * E o PathVariable para representar o id de um recurso ou para navegar pelos seus subrecursos. 
 * Ex:
 * /livros/21 -> detalhes do livro(recurso) de id 21
 * /livros/21/autores -> detalhes dos autores(subrecurso) do livro de id 21
 * /livros/21/autores/2 -> detalhes do autor de id 2 do livro de id 21
 * 
 * Não é uma regra, mas é uma prática comum em APIs Rest.
 * 
 * */
