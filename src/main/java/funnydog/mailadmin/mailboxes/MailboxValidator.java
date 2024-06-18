package funnydog.mailadmin.mailboxes;

import funnydog.mailadmin.domains.Domain;
import funnydog.mailadmin.domains.DomainRepository;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MailboxValidator implements Validator {

	@Autowired
	private DomainRepository domainRepository;

	@Override
	public boolean supports(Class<?> klass) {
		return Mailbox.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Mailbox mailbox = (Mailbox)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "mailbox.email.required");

		// validation against the domain
		Domain domain = domainRepository.findById(mailbox.getDomainId());
		if (domain == null) {
			errors.reject("mailbox.domain.unknown");
		} else if (!mailbox.getEmail().endsWith(domain.getName())) {
			errors.rejectValue("email", "mailbox.email.domain_mismatch");
		}
	}
}
