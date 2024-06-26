package funnydog.mailadmin.mailboxes;

import funnydog.mailadmin.domains.Domain;
import funnydog.mailadmin.domains.DomainRepository;

import java.util.Collection;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mailboxes")
public class MailboxController {

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private MailboxRepository mailboxRepository;

	@Autowired
	private MailboxValidator mailboxValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@InitBinder("mailbox")
	public void initMailboxBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(mailboxValidator);
	}

	@GetMapping("/list")
	public String getList(@RequestParam("domain") Long domainId,
			      ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}
		model.addAttribute("domain", domain);

		Collection<Mailbox> mailboxes = mailboxRepository.findByDomainId(domainId);
		model.addAttribute("mailboxes", mailboxes);

		return "mailbox_list";
	}

	@GetMapping("/form")
	public String getForm(@RequestParam("domain") Long domainId,
			      @RequestParam(name="id", required=false) Long id,
			      ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		Mailbox mailbox = null;
		String title = null;
		if (id != null) {
			mailbox = mailboxRepository.findById(id);
			title = "Change Mailbox";
		}
		if (mailbox == null) {
			mailbox = new Mailbox();
			mailbox.setDomainId(domainId);
			title = "New Mailbox";
		}

		model.addAttribute("domain", domain);
		model.addAttribute("mailbox", mailbox);
		model.addAttribute("title", title);
		return "mailbox_form";
	}

	@PostMapping("/form")
	public String postForm(@RequestParam("domain") Long domainId,
			       @Valid Mailbox mailbox,
			       BindingResult result,
			       RedirectAttributes redirectAttributes,
			       ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		if (result.hasErrors()) {
			model.addAttribute("domain", domain);
			model.addAttribute("mailbox", mailbox);
			model.addAttribute("title",
					   mailbox.getId() == null
					   ? "New Mailbox"
					   : "Change Mailbox");
			return "mailbox_form";
		}

		Mailbox existing = mailboxRepository.findById(mailbox.getId());
		if (existing == null) {
			existing = new Mailbox();
			existing.setDomainId(domain.getId());
		}

		// update the fields
		existing.setEmail(mailbox.getEmail());
		existing.setActive(mailbox.getActive());

		// if empty the password isn't changed
		// otherwise we should encode it
		String password = mailbox.getPassword();
		if (password != null && !password.isEmpty()) {
			String encoded = passwordEncoder.encode(password);
			existing.setPassword(encoded);
		}

		String message;
		if (existing.getId() != null) {
			mailboxRepository.update(existing);
			message = "The mailbox was successfully updated.";
		} else {
			mailboxRepository.create(existing);
			message = "The mailbox was successfully created.";
		}
		redirectAttributes.addFlashAttribute("flashMessages", message);
		return "redirect:/mailboxes/list?domain=" + mailbox.getDomainId();
	}

	@GetMapping("/delete")
	public String getDelete(@RequestParam("domain") Long domainId,
				@RequestParam("id") Long id,
				ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}
		Mailbox mailbox = mailboxRepository.findById(id);
		if (mailbox == null) {
			return "redirect:/mailboxes/list?domain=" + domainId;
		}

		model.addAttribute("domain", domain);
		model.addAttribute("mailbox", mailbox);
		return "mailbox_delete";
	}

	@PostMapping("/delete")
	public String postDelete(@RequestParam("domain") Long domainId,
				 Long id,
				 RedirectAttributes redirectAttributes) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		String message;
		if (mailboxRepository.deleteById(id) != 0) {
			message = "The mailbox was successfully deleted.";
		} else {
			message = "Couldn't find the mailbox to delete.";
		}
		redirectAttributes.addFlashAttribute("flashMessages", message);
		return "redirect:/mailboxes/list?domain=" + domainId;
	}
}
