package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsUserTrackerLog<TR extends UtilsUserTracker<TR,UL,U,T,S,L,D>,
								 UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L,D>,
								 U extends EjbWithId,
								 T extends UtilsStatus<L,D>,
								 S extends UtilsStatus<L,D>,
								 L extends UtilsLang,
								 D extends UtilsDescription>
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