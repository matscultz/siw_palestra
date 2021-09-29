package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.model.Prenotazione;

public interface LezioneRepository extends CrudRepository<Lezione, Long> {

	public Optional<Lezione> findById(Long id);
	public List<Lezione> findByData(LocalDate data);
	public List<Lezione> findByOrario(LocalTime orario);
	public List<Lezione> findByDataAndCorso(LocalDate data, Corso corso);
	
	@Query(value="select * from lezione where corso_id=?1 and data > current_date order by data, orario ",nativeQuery=true)
    public List<Lezione> lezioniOrdinate(Long id);
}