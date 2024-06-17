package funnydog.mailadmin.domains;

import java.time.ZonedDateTime;

public class Domain {
	private Long id;
	private String name;
	private String description;
	private Boolean active;
	private Boolean backup;
	private ZonedDateTime created;
	private ZonedDateTime modified;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getBackup() {
		return backup;
	}
	public void setBackup(Boolean backup) {
		this.backup = backup;
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
