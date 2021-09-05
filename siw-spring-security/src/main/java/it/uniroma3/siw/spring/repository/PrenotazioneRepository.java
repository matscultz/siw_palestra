package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	public List<Prenotazione> findByDataAndUser(LocalDate data, User cliente);
	
}