package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Insegnante;
import it.uniroma3.siw.spring.repository.InsegnanteRepository;

@Service
public class InsegnanteService {
	
	@Autowired
	private InsegnanteRepository insegnanteRepository; 
	
	@Transactional
	public Insegnante inserisci(Insegnante insegnante) {
		return insegnanteRepository.save(insegnante);
	}

	@Transactional
	public List<Insegnante> tutti() {
		return (List<Insegnante>) insegnanteRepository.findAll();
	}

	@Transactional
	public Insegnante insegnantePerId(Long id) {
		Optional<Insegnante> optional = insegnanteRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Insegnante insegnante) {
		List<Insegnante> insegnanti = this.insegnanteRepository.findByNomeAndCognome(insegnante.getNome(), insegnante.getCognome());
		if (insegnanti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteInsegnanteByID(Long id) {
		insegnanteRepository.deleteById(id);
	}
}
