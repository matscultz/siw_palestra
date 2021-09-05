package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Insegnante;

public interface InsegnanteRepository extends CrudRepository<Insegnante, Long> {

	public List<Insegnante> findByNome(String nome);
	public List<Insegnante> findByCognome(String cognome);
	public List<Insegnante> findByNomeOrCognome(String nome, String cognome);
	public List<Insegnante> findByNomeAndCognome(String nome, String cognome);
	
}