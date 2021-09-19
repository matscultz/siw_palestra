package it.uniroma3.siw.spring.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory;

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

import it.uniroma3.siw.spring.controller.validator.InsegnanteValidator;
import it.uniroma3.siw.spring.model.Insegnante;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.InsegnanteService;
import it.uniroma3.siw.spring.model.Corso;

@Controller
public class InsegnanteController {
	
	@Autowired
	private InsegnanteService insegnanteService;
	
    @Autowired
    private InsegnanteValidator insegnanteValidator;

  
   // private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
    @RequestMapping(value="/admin/insegnante", method = RequestMethod.GET)
    public String addInsegnante(Model model) {
    	model.addAttribute("insegnante", new Insegnante());
    	model.addAttribute("corsi",this.insegnanteService.getCorsoService().tutti());
        return "insegnanteForm";
    }

    @RequestMapping(value = "/insegnante/{id}", method = RequestMethod.GET)
    public String getInsegnante(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("insegnante", this.insegnanteService.insegnantePerId(id));
    	return "insegnante";
    }

    @RequestMapping(value = "/insegnante", method = RequestMethod.GET)
    public String getInsegnanti(Model model) {
    		model.addAttribute("insegnanti", this.insegnanteService.tutti());
    		return "insegnanti";
    }
    
    @RequestMapping(value = "/admin/insegnante", method = RequestMethod.POST)
    public String addInsegnante(@ModelAttribute("insegnante") Insegnante insegnante, 
    									Model model, BindingResult bindingResult) {
    	this.insegnanteValidator.validate(insegnante, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.insegnanteService.inserisci(insegnante);
            model.addAttribute("insegnanti", this.insegnanteService.tutti());
            return "insegnanti";
        }
        return "insegnanteForm";
    }
    @RequestMapping(value="/admin/insegnante/{id}", method= RequestMethod.GET)
    public String deleteInsegnante(@PathVariable("id")Long id, Model model) {
//		logger.debug("inzio eliminazione");
    		this.insegnanteService.deletedInsegnante(id);
  
    		model.addAttribute("insegnanti",this.insegnanteService.tutti());
        	model.addAttribute("role", this.insegnanteService.getCredentialsService().getRoleAuthenticated());

    		return "insegnanti";	
    }
}

