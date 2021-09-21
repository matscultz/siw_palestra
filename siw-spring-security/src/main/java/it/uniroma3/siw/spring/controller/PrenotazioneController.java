package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.ProdottoValidator;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Prodotto;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.ProdottoService;
import it.uniroma3.siw.spring.service.UserService;

public class PrenotazioneController {

		@Autowired
		private PrenotazioneService prenotazioneService;
		@Autowired
		private UserService userService;
		@Autowired
		private CorsoService corsoService;
	        
	  /*   @RequestMapping(value="/prenotazione", method = RequestMethod.GET)
	    public String creaPrenotazione(Model model) {
	    	model.addAttribute("prodotto", new Prodotto());
	        return "prodottoForm";
	    } */

	    @RequestMapping(value = "/prenotazione/{id}", method = RequestMethod.GET)
	    public String getPrenotazione(@PathVariable("id") Long id, Model model) {
	    	model.addAttribute("prenotazione", this.prenotazioneService.prenotazionePerId(id));
	    	return "prenotazione";
	    }

	    @RequestMapping(value = "/prenotazione", method = RequestMethod.GET)
	    public String mostraPrenotazioni(Model model) {
	    		model.addAttribute("prenotazione", this.prenotazioneService.tutti());
	    		return "prenotazioni";
	    }
	    
	    @RequestMapping(value = "/prenotazione", method = RequestMethod.POST)
	    public String aggiungiPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione, 
	    									Model model, BindingResult bindingResult) {
	    	/* this.prenotazioneService.validate(prenotazione, bindingResult); */
	        if (!bindingResult.hasErrors()) {
	        	this.prenotazioneService.inserisci(prenotazione);
	            model.addAttribute("prenotazioni", this.prenotazioneService.tutti());
	            return "prenotazioni";
	        }
	        return "prenotazione";
	    }
}
