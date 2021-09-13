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
import it.uniroma3.siw.spring.controller.validator.UserValidator;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "userForm";
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", this.userService.getUser(id));
		return "user";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUsers(Model model) {
		model.addAttribute("users", this.userService.tutti());
		return "users";
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, Model model, BindingResult bindingResult) {
		this.userValidator.validate(user, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.userService.inserisci(user);
			model.addAttribute("users", this.userService.tutti());
			return "users";
		}
		return "userForm";
	}
}
