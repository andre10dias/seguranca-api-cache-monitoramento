package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport //Paginação
@EnableCaching //cache
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}

/*
 * @EnableSpringDataWebSupport -> Habilita a recuperacao dos parâmetros de paginação quando
 * quando uma variável do tipo 'Pageable' é passado diretamente como parâmetro do método.
 * 
 * Com isso o endpoint ficaria desta forma:
 * http://localhost:8080/topicos?page=0&size=10&sort=id,desc
 * 
 * Podendo passar também mais de um campo para ordenação
 * http://localhost:8080/topicos?page=0&size=10&sort=id,desc&&dataCriacao,desc
 *  
 * */
