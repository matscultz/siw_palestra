package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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

import it.uniroma3.siw.spring.controller.validator.LezioneValidator;
import it.uniroma3.siw.spring.controller.validator.modificaLezioneValidator;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.LezioneService;
import it.uniroma3.siw.spring.service.PrenotazioneService;

@Controller
public class LezioneController {
	
	@Autowired
	private LezioneService lezioneService;
	@Autowired
    private LezioneValidator lezioneValidator;
	@Autowired
	private modificaLezioneValidator modificaLezioneValidator;
    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private CredentialsService credentialsService;
        
    @RequestMapping(value="/admin/lezione", method = RequestMethod.GET)
    public String addLezione(Model model) {
    	model.addAttribute("lezione", new Lezione());
    	model.addAttribute("corsi", this.lezioneService.getCorsoService().tutti());
    	model.addAttribute("insegnanti", this.lezioneService.getInsegnanteService().tutti());
        return "lezioneForm";
    }

    @RequestMapping(value = "/lezione/{id}", method = RequestMethod.GET)
    public String getLezione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("lezione", this.lezioneService.lezionePerId(id));
    	return "lezione";
    }

    @RequestMapping(value = "/lezione", method = RequestMethod.GET)
    public String getLezioni(Model model) {
    		model.addAttribute("lezioni", this.lezioneService.tutti());
    		return "lezioni";
    }
    
    @RequestMapping(value = "/admin/lezione", method = RequestMethod.POST)
    public String addLezione(@ModelAttribute("lezione") Lezione lezione, 
    									Model model, BindingResult bindingResult) {
    	this.lezioneValidator.validate(lezione, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.lezioneService.inserisci(lezione);
            model.addAttribute("lezioni", this.lezioneService.tutti());
            return "lezioni";
        }
        return "lezioneForm";
    }
    @RequestMapping(value="/admin/lezione/{id}", method= RequestMethod.GET)
    public String deleteLezione(@PathVariable("id")Long id, Model model) {
    		this.lezioneService.deletedLezione(id);
    		model.addAttribute("lezioni",this.lezioneService.tutti());
        	model.addAttribute("role", this.lezioneService.getCredentialsService().getRoleAuthenticated());

    		return "lezioni";	
    }
    
    @RequestMapping(value = "/lezione/{id}", method = RequestMethod.POST)
    public String prenotaLezione(@ModelAttribute("prenotazione") Prenotazione prenotazione, @PathVariable("id") Long id, Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        User user = credentials.getUser();
        prenotazione.setCliente(user);
        prenotazione.setLezione(this.lezioneService.lezionePerId(id));
        this.prenotazioneService.inserisci(prenotazione);
        user.getPrenotazioni().add(prenotazione);
        return "redirect:/prenotazione";
    }
    
    @RequestMapping(value="/admin/updLezione/{id}",method=RequestMethod.GET)
    public String getModificaLezione(@PathVariable("id")Long id,Model model) {
    	
    	model.addAttribute("lezione", this.lezioneService.lezionePerId(id));
    	model.addAttribute("corsi", this.lezioneService.getCorsoService().tutti());
    	model.addAttribute("insegnanti", this.lezioneService.getInsegnanteService().tutti());
        return"/updLezione";
    }

    @RequestMapping(value="/admin/updLezione/{id}",method=RequestMethod.POST)
    public String modificaLezione(@ModelAttribute("lezione")Lezione lezione, Model model, BindingResult bindingResult)
    {
        this.modificaLezioneValidator.validate(lezione, bindingResult);
        if(!bindingResult.hasErrors()) {
        	this.lezioneService.inserisci(lezione);
            return"redirect:/lezione";
        }
        return"redirect:/default";
    }

}