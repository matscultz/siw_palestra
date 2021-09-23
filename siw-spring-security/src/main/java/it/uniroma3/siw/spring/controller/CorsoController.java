package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.FileUploadUtil;
import it.uniroma3.siw.spring.controller.validator.CorsoValidator;
import it.uniroma3.siw.spring.model.Corso;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.CorsoRepository;
import it.uniroma3.siw.spring.service.CorsoService;
import it.uniroma3.siw.spring.service.InsegnanteService;
import it.uniroma3.siw.spring.service.LezioneService;

@Controller
public class CorsoController {
	
	@Autowired
	private CorsoService corsoService;
	@Autowired
	private CorsoRepository corsoRepository;	
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
    @PostMapping("/corsiSave")
    public String saveCorso(@ModelAttribute Corso corso,
 Model model ,      @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult) throws IOException {
        if(!bindingResult.hasErrors()) { 
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        corso.setPhotos(fileName);
         
       Corso savedCorso = corsoRepository.save(corso);
 
        String uploadDir = "corso-photos/" + savedCorso.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        model.addAttribute("corsi", this.corsoService.tutti());
        
        return "corsi";}
        
        else
        	return "corsoForm";
    }

    @RequestMapping(value = "/corso/{id}", method = RequestMethod.GET)
    public String getCorso(@PathVariable("id") Long id, Model model) {
    	Corso corso = this.corsoService.corsoPerId(id);
    	model.addAttribute("corso", corso);
    	/* model.addAttribute("corso", this.corsoService.corsoPerId(id)); */
    	model.addAttribute("lezioni", corso.getLezioni());
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
    @RequestMapping(value="/admin/corso/{id}", method= RequestMethod.GET)
    public String deleteCorso(@PathVariable("id")Long id, Model model) {
    		this.corsoService.deletedCorso(id);
    		model.addAttribute("corsi",this.corsoService.tutti());
        	model.addAttribute("role", this.corsoService.getCredentialsService().getRoleAuthenticated());

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
