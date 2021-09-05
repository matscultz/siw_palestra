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
		Optional<Lezione> lezioni = this.lezioneRepository.findById(lezione.getId());
		if (lezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteLezioneByID(Long id) {
		lezioneRepository.deleteById(id);
	}
}