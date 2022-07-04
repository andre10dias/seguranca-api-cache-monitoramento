package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	//Injetando o token service via construtor
	AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		Boolean valido = tokenService.isTokenValido(token);
		System.out.println(valido);
		
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		//https://cursos.alura.com.br/forum/topico-token-retornando-null-184163
		if (token == null || token.isEmpty() /*|| token.startsWith("Bearer ")*/) {
			return null;
		}
		
		//retorna o conteúdo do token sem o 'Bearer'
		return token.substring(7, token.length());
	}

}
