package funnydog.mailadmin.aliases;

import java.time.ZonedDateTime;

public class Alias {
	public Long id;
	public Long domainId;
	public String destination;
	public String redirect;
	public Boolean active;
	public ZonedDateTime created;
	public ZonedDateTime modified;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getDomainId() {
		return domainId;
	}
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	public ZonedDateTime getCreated() {
		return created;
	}
	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}

	public ZonedDateTime getModified() {
		return modified;
	}
	public void setModified(ZonedDateTime modified) {
		this.modified = modified;
	}
}
