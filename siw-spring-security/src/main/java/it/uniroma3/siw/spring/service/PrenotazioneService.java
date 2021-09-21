package it.uniroma3.siw.spring.service;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository; 
	@Autowired
	private UserService userService;
	@Autowired
	private CredentialsService credentialsService;
	
	@Transactional
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}

	@Transactional
	public List<Prenotazione> tutti() {
		return (List<Prenotazione>) prenotazioneRepository.findAll();
	}
	
	@Transactional
	public List<Prenotazione> listaPUtente(Long id) {
		return (List<Prenotazione>) prenotazioneRepository.findByUser(id);
	}

	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> optional = prenotazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
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

	public List<Prenotazione> prenotazioniUtente() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        User cliente = credentials.getUser();
		return cliente.getPrenotazioni();
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
}