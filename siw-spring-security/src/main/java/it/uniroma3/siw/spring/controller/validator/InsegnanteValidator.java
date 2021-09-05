package it.uniroma3.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Insegnante;
import it.uniroma3.siw.spring.service.InsegnanteService;


@Component
public class InsegnanteValidator implements Validator {
	@Autowired
	private InsegnanteService insegnanteService;
	
    private static final Logger logger = LoggerFactory.getLogger(InsegnanteValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.insegnanteService.alreadyExists((Insegnante)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Insegnante.class.equals(aClass);
	}
}
