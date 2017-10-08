package br.com.caelum.ingresso.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@ImportResource("/WEB-INF/spring-context.xml")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.
			csrf().disable().authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/compra/**").hasRole("COMPRADOR")
				.antMatchers("/filme/**").permitAll()
				.antMatchers("/sessao/**/lugares").permitAll()
				.antMatchers("/magic/**").permitAll()
				.antMatchers("/").permitAll()
			.anyRequest().authenticated().and()
				.formLogin()
					.usernameParameter("email")
					.loginPage("/login")
					.permitAll()
				.and()
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/assets/**");
	}

	/*
	 * Aqui informa-se qual o algorítimo para criptografar a senha.
	 * Existem diversos algoritmos, como MD5, SHA1, SHA1-256, SHA1-512, BCrypt entre outros...
	 * Neste caso utilizou-se o BCrypt, que é recomendado na documentação do Spring Security.
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
