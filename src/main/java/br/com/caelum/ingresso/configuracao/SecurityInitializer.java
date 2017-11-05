package br.com.caelum.ingresso.configuracao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{

	private Logger logger = Logger.getGlobal();
	
	public SecurityInitializer() {
		super(SecurityConfiguration.class);
		logger.log(Level.INFO, 
				"Executou super(SecurityConfiguration.class) em SecurityInitializer");
	}
}
