package funnydog.mailadmin.mailboxes;

import funnydog.mailadmin.domains.Domain;
import funnydog.mailadmin.domains.DomainRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/list")
	public String getList(@RequestParam("domain") Long domainId,
			      ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
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
	public String postForm(Mailbox mailbox,
			       BindingResult bindingResult,
			       RedirectAttributes redirectAttributes,
			       ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("domain", domainRepository.findById(mailbox.getDomainId()));
			model.addAttribute("mailbox", mailbox);
			return "mailbox_form";
		}

		String message;
		if (mailbox.getId() != null) {
			// if the password is null set it to the old password
			if (mailbox.getPassword() == null) {
				Mailbox oldMailbox = mailboxRepository.findById(mailbox.getId());
				if (oldMailbox != null) {
					mailbox.setPassword(oldMailbox.getPassword());
				}
			}
			mailboxRepository.update(mailbox);
			message = "The mailbox was successfully updated.";
		} else {
			mailboxRepository.create(mailbox);
			message = "The mailbox was successuflly created.";
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
