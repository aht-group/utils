package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsTrackerLog<TR extends UtilsTracker<TR,TL,T,S,L,D>,
								 TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
								 T extends UtilsStatus<L,D>, S extends UtilsStatus<L,D>,
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