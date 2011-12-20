package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsUserTrackerLog<TR extends UtilsUserTracker<TR,UL,U,T,S,L>,
								 UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L>,
								 U extends EjbWithId,
								 T extends UtilsStatus<L>,
								 S extends UtilsStatus<L>,
								 L extends UtilsLang>
		extends EjbWithId
{
	TR getTracker();
	void setTracker(TR tracker);
	
	U getUser();
	void setUser(U user);
	
	S getStatus();
	void setStatus(S status);
	
	Date getRecord();
	void setRecord(Date record);
}