package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.Insegnante;
import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.repository.CorsoRepository;

@Service
public class CorsoService {

	@Autowired
	private CorsoRepository corsoRepository;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private InsegnanteService insegnanteService; 
	@Autowired
	private CorsoService corsoService;

	@Transactional
	public Corso inserisci(Corso corso) {
		return corsoRepository.save(corso);
	}

	@Transactional
	public List<Corso> tutti() {
		return (List<Corso>) corsoRepository.findAll();
	}

	@Transactional
	public Corso corsoPerId(Long id) {
		Optional<Corso> optional = corsoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			return null;
	}

	@Transactional
	public boolean alreadyExists(Corso corso) {
		List<Corso> corsi = this.corsoRepository.findByNome(corso.getNome());
		if (corsi.size() > 0)
			return true;
		else
			return false;
	}

	@Transactional
	public void deleteCorsoByID(Long id) {
		corsoRepository.deleteById(id);
	}

	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	@Transactional
	public boolean deletedCorso(Long id) {
		try {
			this.corsoRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public InsegnanteService getInsegnanteService() {
		return this.insegnanteService;
	}
	public CorsoService getCorsoService() {
		return this.corsoService;
	}
	
	@Transactional
    public List<Lezione> lezioniCorso(Long id){
        Optional<Corso> corso = corsoRepository.findById(id);
        List<Lezione> lezione = new ArrayList<>();
        Corso corsi = corso.get();
        lezione = corsi.getLezioni();
        return lezione;
    }

    @Transactional
    public List<Insegnante> insegnantiCorso(Long id){
        Optional<Corso> corso = corsoRepository.findById(id);
        List<Insegnante> insegnanti= new ArrayList<>();
        Corso corsi = corso.get();
        return insegnanti = corsi.getInsegnanti();
    }
}