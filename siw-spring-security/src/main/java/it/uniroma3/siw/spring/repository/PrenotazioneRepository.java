package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	@Query(value="select * from prenotazione where cliente_id=?1",nativeQuery=true)
    public List<Prenotazione> findByUser(Long id);
	
	@Query(value="select * from prenotazione p join lezione l on p.lezione_id = l.id where cliente_id=?1 and data > current_date order by data, orario ",nativeQuery=true)
    public List<Prenotazione> prenotazioniOrdinate(Long id);
}