package org.jeesl.interfaces.model.io.revision.data;

import java.util.Date;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslRevision extends EjbWithId
{					
	long getId();
	void setId(long id);

	Date getAuditRecord();
	void setAuditRecord(Date auditRecord);

	Long getUserId();
	void setUserId(Long userId);
	
	String getIpAddress();
	void setIpAddress(String ipAddress);
}