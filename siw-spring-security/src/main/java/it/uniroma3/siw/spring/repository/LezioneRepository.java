package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Lezione;

public interface LezioneRepository extends CrudRepository<Lezione, Long> {

	public Optional<Lezione> findById(Long id);
	public List<Lezione> findByData(LocalDate data);
	public List<Lezione> findByOrario(LocalTime orario);
	public List<Lezione> findByDataAndCorso(LocalDate data, Corso corso);
}