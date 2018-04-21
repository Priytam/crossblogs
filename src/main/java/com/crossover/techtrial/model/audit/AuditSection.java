package com.crossover.techtrial.model.audit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class AuditSection implements Serializable {

	private static final long serialVersionUID = -1934446958975060889L;

	@Column(name = "DATE_CREATED")
	LocalDateTime dateCreated;

	@Column(name = "DATE_MODIFIED")
	LocalDateTime dateModified;


	public AuditSection() {
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(LocalDateTime dateModified) {
		this.dateModified = dateModified;
	}
}