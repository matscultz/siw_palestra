package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.repository.LezioneRepository;

@Service
public class LezioneService {
	
	@Autowired
	private LezioneRepository lezioneRepository; 
	@Autowired
	private LezioneService lezioneService; 
	@Autowired
	private CorsoService corsoService;
	@Autowired
	private InsegnanteService insegnanteService;
	@Autowired
	private CredentialsService credentialsService;
	
	@Transactional
	public Lezione inserisci(Lezione lezione) {
		return lezioneRepository.save(lezione);
	}

	@Transactional
	public List<Lezione> tutti() {
		return (List<Lezione>) lezioneRepository.findAll();
	}

	@Transactional
	public Lezione lezionePerId(Long id) {
		Optional<Lezione> optional = lezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Lezione lezione) {
		List<Lezione> lezioni = this.lezioneRepository.findByDataAndCorso(lezione.getData(), lezione.getCorso());
		if (lezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteLezioneByID(Long id) {
		lezioneRepository.deleteById(id);
	}

	public LezioneService getLezioneService() {
		
		return this.lezioneService;
	}

	public CorsoService getCorsoService() {
		return this.corsoService;
	}

	public InsegnanteService getInsegnanteService() {
		return this.insegnanteService;
	}
	
	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}
	@Transactional
	public boolean deletedLezione(Long id) {
		try {
			this.lezioneRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
}
}