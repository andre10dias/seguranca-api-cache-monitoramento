package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	//Configurações de autenticação (login)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//configurações de autorização (urls, perfis de acesso)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll() //liberando acesso aos endpoints
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.anyRequest().authenticated() //só libera as demais requisições se estiver autenticado
			.and().formLogin(); //gera um formulário de login do próprio spring
	}
	
	//configurações de recursos estáticos (js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	// Só p pegar a senha 123456 encriptada
	//public static void main(String[] args) {
	//	System.out.println(new BCryptPasswordEncoder().encode("123456"));
	//}
	
	//Os métodos acima foram depreciados a partir da versão 5.4 do spring security
	
	//@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	//	return http
	//            .antMatcher("/topicos")
	//            .antMatcher("/topicos/*")
	//            .authorizeRequests(authorize -> authorize
	//                    .anyRequest().authenticated()
	//            )
	//            .build();
    //}

}
