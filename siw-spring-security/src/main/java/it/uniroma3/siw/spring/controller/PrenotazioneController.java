package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.InsegnanteService;
import it.uniroma3.siw.spring.service.LezioneService;
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.UserService;
import jdk.internal.org.jline.utils.Log;

@Controller
public class PrenotazioneController {

	   @Autowired
		private PrenotazioneService prenotazioneService;
		@Autowired
		private UserService userService;
		@Autowired
		private CorsoService corsoService;
		@Autowired
		private CredentialsService credentialsService;
		@Autowired
		private LezioneService lezioneService;
		@Autowired
		private InsegnanteService insegnanteService;
	        
	    @RequestMapping(value = "/prenotazione/{id}", method = RequestMethod.GET)
	    public String getPrenotazione(@PathVariable("id") Long id, Model model) {
	    	model.addAttribute("prenotazione", this.prenotazioneService.prenotazionePerId(id));
	    	return "prenotazione";
	    }

	    @RequestMapping(value = "/prenotazione", method = RequestMethod.GET)
	    public String mostraPrenotazioniDellUtente(Model model) {
	    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
            User cliente = credentials.getUser();
            model.addAttribute("prenotazioni", this.prenotazioneService.listaPUtente(cliente.getId()));
	    	return "prenotazioni";
	    }
	    
	    @RequestMapping(value="/prenotazioneDelete/{id}", method= RequestMethod.GET)
	    public String deletePrenotazione(@PathVariable("id")Long id, Model model) {
	    		this.prenotazioneService.deletedPrenotazione(id);
	    		model.addAttribute("prenotazioni",this.prenotazioneService.tutti());
	        	model.addAttribute("role", this.prenotazioneService.getCredentialsService().getRoleAuthenticated());
	        
	    		return "prenotazioni";	
	    }
	    
	    
	    /* @RequestMapping(value = "/prenotazione", method = RequestMethod.POST)
	    public String aggiungiPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione, 
	    									Model model, BindingResult bindingResult) {
	    	/* this.prenotazioneService.validate(prenotazione, bindingResult); */
	    	
	     /*	List<Prenotazione> prenotazioneCliente = new ArrayList<Prenotazione>();
	    	prenotazioneCliente = this.prenotazioneService.prenotazioniUtente();
	        if (!bindingResult.hasErrors()) {
	        	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	            Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
	            User cliente = credentials.getUser();
	            prenotazione.setCliente(cliente);
	        	this.prenotazioneService.inserisci(prenotazione);
	            model.addAttribute("prenotazioni", this.prenotazioneService.listaPUtente(cliente.getId()));
	            return "prenotazioni";
	        }
	        return "prenotazione";
	    } */

	
}
