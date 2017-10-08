package br.com.caelum.ingresso.configuracao;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{

	public SecurityInitializer() {
		super(SecurityConfiguration.class);
	}
}
