package net.sf.ahtutils.model.interfaces.tracker;

import java.util.Date;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTrackerLog<TR extends UtilsTracker<TR,TL,T,S,L,D>,
								 TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
								 T extends JeeslStatus<T,L,D>,
								 S extends JeeslStatus<S,L,D>,
								 L extends JeeslLang,
								 D extends JeeslDescription>
		extends EjbWithId
{
	TR getTracker();
	void setTracker(TR tracker);
	
	S getStatus();
	void setStatus(S status);
	
	Date getRecord();
	void setRecord(Date record);
}