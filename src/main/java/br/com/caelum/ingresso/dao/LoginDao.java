package br.com.caelum.ingresso.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Usuario;

@Repository
public class LoginDao implements UserDetailsService{

	@PersistenceContext
	private EntityManager manager;
	
	private Logger logger = Logger.getGlobal();
	
	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {
		
		logger.log(Level.INFO, 
				"Vai executar o loadUserByUsername em LoginDao com parametro: " 
				+ email);
		
		try {
			logger.log(Level.INFO, 
					"Vai executar a query.");
			
			UserDetails userDetail = manager.createQuery(
					"select u from Usuario u where u.email = :email", 
					Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
			
			logger.log(Level.INFO, 
					"Detalhes do usuario retornado: " 
					+ userDetail +
					" : " + userDetail.getPassword() +
					" : " + userDetail.getUsername() +
					" : " + userDetail.getAuthorities().size());
			
			return userDetail;
			
		}catch(NoResultException e) {
			logger.log(Level.WARNING, 
					"Caiu no erro " + e.getMessage());
			throw new UsernameNotFoundException("Email " + email + " não encontrado.");
		}
	}
}
