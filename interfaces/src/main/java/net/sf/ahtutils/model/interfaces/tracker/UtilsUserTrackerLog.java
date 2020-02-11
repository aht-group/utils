package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsUserTrackerLog<TR extends UtilsUserTracker<TR,UL,U,T,S,L,D>,
								 UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L,D>,
								 U extends EjbWithId,
								 T extends JeeslStatus<S,L,D>,
								 S extends JeeslStatus<S,L,D>,
								 L extends JeeslLang,
								 D extends JeeslDescription>
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