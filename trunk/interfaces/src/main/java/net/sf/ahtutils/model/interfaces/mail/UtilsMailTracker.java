package net.sf.ahtutils.model.interfaces.mail;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsMailTracker<S extends UtilsStatus<L,D>,
									L extends UtilsLang,
									U extends EjbWithId,
									D extends UtilsDescription>
						extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	S getType();
	void setType(S type);
	
	Date getRecordCreated();
	void setRecordCreated(Date recordGenerated);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	int getRetryCounter();
	void setRetryCounter(int retryCounter);
	
	U getUser();
	void setUser(U user);
}