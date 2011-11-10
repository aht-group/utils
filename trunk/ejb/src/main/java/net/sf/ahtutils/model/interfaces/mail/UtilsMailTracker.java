package net.sf.ahtutils.model.interfaces.mail;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsMailTracker<T extends UtilsStatus<L>, L extends UtilsLang> extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	T getType();
	void setType(T type);
	
	Date getRecordCreated();
	void setRecordCreated(Date recordGenerated);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
}