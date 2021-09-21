package it.uniroma3.siw.spring.service;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository; 
	@Autowired
	private UserService userService;
	
	@Transactional
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}

	@Transactional
	public List<Prenotazione> tutti() {
		return (List<Prenotazione>) prenotazioneRepository.findAll();
	}

	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> optional = prenotazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	/* @Transactional
	public Prenotazione prenotazionePerCorso(Corso corso) {
		List<Prenotazione> optional = prenotazioneRepository.findByCorso(corso));
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	} */

	/* @Transactional
	public boolean alreadyExists(Prenotazione prenotazione) {
		List<Prenotazione> prenotazioni = this.prenotazioneRepository.findById();
		if (prenotazioni.size() > 0)
			return true;
		else 
			return false;
	} */
	
	@Transactional
	public void deletePrenotazioneByID(Long id) {
		prenotazioneRepository.deleteById(id);
	}
	
	@Transactional
	public boolean deletedPrenotazione(Long id) {
		try {
			this.prenotazioneRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
}
}