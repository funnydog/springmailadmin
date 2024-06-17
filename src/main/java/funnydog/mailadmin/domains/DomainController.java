package funnydog.mailadmin.domains;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/domains")
public class DomainController {

	@Autowired
	private DomainRepository repository;

	@GetMapping("/list")
	public String getDomainList(ModelMap model) {
		Collection<Domain> domains = repository.findAll();

		model.addAttribute("domains", domains);
		return "domain_list";
	}

	@GetMapping("/overview")
	public String getDomainOverview(@RequestParam("id") Long id, ModelMap model) {
		Domain domain = repository.findById(id);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		model.addAttribute("domain", domain);
		return "domain_overview";
	}

	@GetMapping("/form")
	public String getForm(@RequestParam(name="id", required=false) Long id, ModelMap model) {
		Domain domain = null;
		String title = null;
		if (id != null) {
			domain = repository.findById(id);
			title = "Change Domain";
		}
		if (domain == null) {
			domain = new Domain();
			domain.setActive(true);
			title = "New Domain";
		}
		model.addAttribute("domain", domain);
		model.addAttribute("title", title);
		return "domain_form";
	}

	@PostMapping("/form")
	public String postForm(Domain domain,
			       BindingResult bindingResult,
			       RedirectAttributes redirectAttributes) {
		String message;
		if (bindingResult.hasErrors()) {
			return "domain_form";
		}

		if (domain.getId() != null) {
			repository.update(domain);
			message = "The domain was successfully updated.";
		} else {
			repository.create(domain);
			message = "The domain was successfully created.";
		}
		redirectAttributes.addFlashAttribute("flashMessages", message);
		return "redirect:/domains/list";
	}

	@GetMapping("/delete")
	public String getDelete(@RequestParam("id") Long id, ModelMap model) {
		Domain domain = repository.findById(id);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		model.addAttribute("domain", domain);
		return "domain_delete";
	}

	@PostMapping("/delete")
	public String postDelete(Long id,
				 ModelMap model,
				 RedirectAttributes redirectAttributes) {
		if (repository.deleteById(id) != 0) {
			redirectAttributes.addFlashAttribute(
				"flashMessages",
				"The domain was successfully deleted.");
		}
		return "redirect:/domains/list";
	}
}
