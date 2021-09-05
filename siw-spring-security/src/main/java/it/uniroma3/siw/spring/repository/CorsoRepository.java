package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Corso;

public interface CorsoRepository extends CrudRepository<Corso, Long> {

	public List<Corso> findByNome(String nome);
	
}