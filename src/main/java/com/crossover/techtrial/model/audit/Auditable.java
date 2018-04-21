package com.crossover.techtrial.model.audit;

public interface Auditable {
	
	AuditSection getAuditSection();
	
	void setAuditSection(AuditSection audit);
}