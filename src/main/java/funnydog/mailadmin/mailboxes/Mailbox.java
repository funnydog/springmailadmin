package funnydog.mailadmin.mailboxes;

import java.time.ZonedDateTime;

public class Mailbox {
	public Long id;
	public Long domainId;
	public String email;
	public String password;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
