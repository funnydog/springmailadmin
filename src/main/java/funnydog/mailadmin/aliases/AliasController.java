package funnydog.mailadmin.aliases;

import funnydog.mailadmin.domains.Domain;
import funnydog.mailadmin.domains.DomainRepository;

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
@RequestMapping("/aliases")
public class AliasController {

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private AliasRepository aliasRepository;

	@GetMapping("/list")
	public String getList(@RequestParam("domain") Long domainId,
			      ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}
		model.addAttribute("domain", domain);

		Collection<Alias> aliases = aliasRepository.findByDomainId(domainId);
		model.addAttribute("aliases", aliases);

		return "alias_list";
	}

	@GetMapping("/form")
	public String getForm(@RequestParam("domain") Long domainId,
			      @RequestParam(name="id", required=false) Long id,
			      ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}
		model.addAttribute("domain", domain);

		Alias alias = null;
		String title = null;
		if (id != null) {
			alias = aliasRepository.findById(id);
			title = "Change Alias";
		}
		if (alias == null) {
			alias = new Alias();
			alias.setDomainId(domain.getId());
			alias.setActive(true);
			title = "New Alias";
		}

		model.addAttribute("alias", alias);
		model.addAttribute("title", title);
		return "alias_form";
	}

	@PostMapping("/form")
	public String postForm(@RequestParam("domain") Long domainId,
			       Alias alias,
			       BindingResult bindingResult,
			       RedirectAttributes redirectAttributes,
			       ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}

		String message = null;
		if (bindingResult.hasErrors()) {
			model.addAttribute("domain", domain);
			model.addAttribute("alias", alias);
			return "alias_form";
		}

		if (alias.getId() != null) {
			aliasRepository.update(alias);
			message = "The alias was successfully updated.";
		} else {
			aliasRepository.create(alias);
			message = "The alias was successfully created.";
		}
		redirectAttributes.addFlashAttribute("flashMessages", message);
		return "redirect:/aliases/list?domain=" + domainId;
	}

	@GetMapping("/delete")
	public String getDelete(@RequestParam("domain") Long domainId,
				@RequestParam("id") Long id,
				ModelMap model) {
		Domain domain = domainRepository.findById(domainId);
		if (domain == null) {
			return "redirect:/domains/list";
		}
		model.addAttribute("domain", domain);

		Alias alias = aliasRepository.findById(id);
		if (alias == null) {
			return "redirect:/aliases/list?domain=" + domainId;
		}
		model.addAttribute("alias", alias);

		return "alias_delete";
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
		if (aliasRepository.deleteById(id) != 0) {
			message = "The alias was sucessfully deleted.";
		} else {
			message = "Couldn't find the alias to delete.";
		}
		redirectAttributes.addFlashAttribute("flashMessages", message);
		return "redirect:/aliases/list?domain=" + domainId;
	}
}
