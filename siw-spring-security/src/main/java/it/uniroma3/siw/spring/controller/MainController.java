package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.CredentialsService;

@Controller
public class MainController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			return "index";
	}
	@RequestMapping(value = {"/contatti"}, method = RequestMethod.GET)
	public String contattaci(Model model) {
		model.addAttribute("role", credentialsService.getRoleAuthenticated());	
		return "contatti";
	}
}