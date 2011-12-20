package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsTrackerLog<TR extends UtilsTracker<TR,TL,T,S,L>,
								 TL extends UtilsTrackerLog<TR,TL,T,S,L>,
								 T extends UtilsStatus<L>, S extends UtilsStatus<L>,
								 L extends UtilsLang>
		extends EjbWithId
{
	TR getTracker();
	void setTracker(TR tracker);
	
	S getStatus();
	void setStatus(S status);
	
	Date getRecord();
	void setRecord(Date record);
}