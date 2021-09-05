package it.uniroma3.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Lezione;
import it.uniroma3.siw.spring.service.LezioneService;


@Component
public class LezioneValidator implements Validator {
	@Autowired
	private LezioneService lezioneService;
	
    private static final Logger logger = LoggerFactory.getLogger(LezioneValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durata", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "data", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orario", "required");
		

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.lezioneService.alreadyExists((Lezione)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Lezione.class.equals(aClass);
	}
}
