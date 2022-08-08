package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * Método para realizar a injeção de dependência do 'AuthenticatedAuthorizationManager'
	 * em AutenticacaoController
	 * */
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//Configurações de autenticação (login)
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(passwordEncoder());
	}
	
	//configurações de autorização (urls, perfis de acesso)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll() //liberando acesso aos endpoints
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
			.anyRequest().authenticated() //só libera as demais requisições se estiver autenticado
			//.and().formLogin(); //gera um formulário de login do próprio spring utiliza a politica de sessão não recomendado para apis rest
			.and().csrf().disable() //desabilita o token do csrf
			.sessionManagement() //alterando a politica de sessão para STATELESS para utilização de token
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(
					new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class
			); //registrando o filtro antes de autenticar
		
		return http.build();
	}
	
	//configurações de recursos estáticos (js, css, imagens, etc.)
	public void configure(WebSecurity web) throws Exception {
	}
	
	// Só p pegar a senha 123456 encriptada
	//public static void main(String[] args) {
	//	System.out.println(new BCryptPasswordEncoder().encode("123456"));
	//}

}
