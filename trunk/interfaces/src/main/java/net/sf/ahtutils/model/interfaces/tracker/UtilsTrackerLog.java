package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTrackerLog<TR extends UtilsTracker<TR,TL,T,S,L,D>,
								 TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
								 T extends UtilsStatus<T,L,D>,
								 S extends UtilsStatus<S,L,D>,
								 L extends UtilsLang,
								 D extends UtilsDescription>
		extends EjbWithId
{
	TR getTracker();
	void setTracker(TR tracker);
	
	S getStatus();
	void setStatus(S status);
	
	Date getRecord();
	void setRecord(Date record);
}