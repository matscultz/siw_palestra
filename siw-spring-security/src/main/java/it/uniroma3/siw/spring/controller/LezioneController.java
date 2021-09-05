package it.uniroma3.siw.spring.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.LezioneValidator;
import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.service.LezioneService;

@Controller
public class LezioneController {
	
	@Autowired
	private LezioneService lezioneService;
	
    @Autowired
    private LezioneValidator lezioneValidator;
        
    @RequestMapping(value="/admin/lezione", method = RequestMethod.GET)
    public String addLezione(Model model) {
    	model.addAttribute("lezione", new Lezione());
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
}