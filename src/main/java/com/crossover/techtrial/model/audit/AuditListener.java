package com.crossover.techtrial.model.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {
	
	@PrePersist
	@PreUpdate
	public void onSaveOrUpdate(Object o) {
		if (o instanceof Auditable) {
			Auditable audit = (Auditable) o;
			AuditSection auditSection = audit.getAuditSection();

			auditSection.setDateModified(LocalDateTime.now());
			if (auditSection.getDateCreated() == null) {
				auditSection.setDateCreated(LocalDateTime.now());
			}
			audit.setAuditSection(auditSection);
		}
	}
}