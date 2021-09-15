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

import it.uniroma3.siw.spring.controller.validator.CorsoValidator;
import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.InsegnanteService;
import it.uniroma3.siw.spring.service.LezioneService;

@Controller
public class CorsoController {
	
	@Autowired
	private CorsoService corsoService;
	
    @Autowired
    private CorsoValidator corsoValidator;
   
    @Autowired
	private InsegnanteService insegnanteService;
    @Autowired
   	private LezioneService lezioneService;     
    
    @RequestMapping(value="/admin/corso", method = RequestMethod.GET)
    public String addCorso(Model model) {
    	model.addAttribute("corso", new Corso());
        return "corsoForm";
    }

    @RequestMapping(value = "/corso/{id}", method = RequestMethod.GET)
    public String getCorso(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("corso", this.corsoService.corsoPerId(id));
    	return "corso";
    }

    @RequestMapping(value = "/corso", method = RequestMethod.GET)
    public String getCorsi(Model model) {
    		model.addAttribute("corsi", this.corsoService.tutti());
    		return "corsi";
    }

    
    @RequestMapping(value = "/admin/corso", method = RequestMethod.POST)
    public String addCorso(@ModelAttribute("corso") Corso corso, 
    									Model model, BindingResult bindingResult) {
    	this.corsoValidator.validate(corso, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.corsoService.inserisci(corso);
            model.addAttribute("corsi", this.corsoService.tutti());
            return "corsi";
        }
        return "corsoForm";
    }
    @RequestMapping(value = "/admin/deleteCorso/{id}", method = RequestMethod.POST)
    public String deleteCorso(@PathVariable("id") Long id) {
    	this.corsoService.deleteCorsoByID(id);
    	return "corsi";
    }
    
    @RequestMapping(value="/admin/modCorso/{id}",method= RequestMethod.GET)
    public String updateCorso(@PathVariable("id")Long id, Model model) {
    
    	model.addAttribute("corso", this.corsoService.corsoPerId(id));
    	model.addAttribute("insegnanti",this.insegnanteService.getInsegnanteService().tutti());
    	model.addAttribute("lezioni",this.lezioneService.getLezioneService().tutti());

        return "corsoFormMod.html";
    }

    
    
}
