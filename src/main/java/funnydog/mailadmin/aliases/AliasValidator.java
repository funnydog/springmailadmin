package funnydog.mailadmin.aliases;

import funnydog.mailadmin.domains.Domain;
import funnydog.mailadmin.domains.DomainRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AliasValidator implements Validator {

	@Autowired
	private DomainRepository domainRepository;

	@Override
	public boolean supports(Class<?> klass) {
		return Alias.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Alias alias = (Alias)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(
			errors, "destination", "alias.destination.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(
			errors, "redirect", "alias.redirect.required");

		// validation against the domain
		Domain domain = domainRepository.findById(alias.getDomainId());
		if (domain == null) {
			errors.reject("alias.domain.unknown");
		} else if (!alias.getDestination().endsWith(domain.getName())) {
			errors.rejectValue("destination", "alias.destination.domain_mismatch");
		}
	}
}
