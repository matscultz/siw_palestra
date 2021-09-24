package it.uniroma3.siw.spring.controller;

import java.io.IOException;

import org.hibernate.annotations.common.util.impl.LoggerFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.FileUploadApplication;

import it.uniroma3.siw.spring.controller.validator.InsegnanteValidator;
import it.uniroma3.siw.spring.model.Insegnante;
import it.uniroma3.siw.spring.repository.InsegnanteRepository;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.InsegnanteService;
import it.uniroma3.siw.spring.model.Corso;

@Controller
public class InsegnanteController {
	
	@Autowired
	private InsegnanteService insegnanteService;
	
    @Autowired
    private InsegnanteValidator insegnanteValidator;

    @Autowired
    private InsegnanteRepository insegnanteRepository;
  
   // private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
    @RequestMapping(value="/admin/insegnante", method = RequestMethod.GET)
    public String addInsegnante(Model model) {
    	model.addAttribute("insegnante", new Insegnante());
    	model.addAttribute("corsi",this.insegnanteService.getCorsoService().tutti());
        return "insegnanteForm";
    }

    @RequestMapping(value = "/insegnante/{id}", method = RequestMethod.GET)
    public String getInsegnante(@PathVariable("id") Long id, Model model) {
    	//model.addAttribute("insegnante", this.insegnanteService.insegnantePerId(id));
    	Insegnante insegnante = this.insegnanteService.insegnantePerId(id);
    	model.addAttribute("insegnante", insegnante);
    	// model.addAttribute("corso", this.corsoService.corsoPerId(id));
    	model.addAttribute("lezioni", insegnante.getLezioni());
    	
    	return "insegnante";
    }

    @RequestMapping(value = "/insegnante", method = RequestMethod.GET)
    public String getInsegnanti(Model model) {
    		model.addAttribute("insegnanti", this.insegnanteService.tutti());
    		return "insegnanti";
    }
    
    @PostMapping("/insegnantiSave")
    public String saveInsegnante(@ModelAttribute Insegnante insegnante,
    			Model model, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult) throws IOException {
        if(!bindingResult.hasErrors()) { 
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        insegnante.setPhotos(fileName);
         
       Insegnante savedInsegnante = insegnanteRepository.save(insegnante);
 
        String uploadDir = "insegnante-photos/" + savedInsegnante.getId();
 
        FileUploadApplication.saveFile(uploadDir, fileName, multipartFile);
        model.addAttribute("insegnanti", this.insegnanteService.tutti());
        
        return "insegnanti";}
        
        else
        	return "insegnanteForm";
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

