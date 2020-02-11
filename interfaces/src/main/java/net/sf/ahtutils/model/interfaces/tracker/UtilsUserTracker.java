package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsUserTracker<TR extends UtilsUserTracker<TR,UL,U,T,S,L,D>,
							  UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L,D>,
							  U extends EjbWithId,
							  T extends JeeslStatus<S,L,D>,
							  S extends JeeslStatus<S,L,D>,
							  L extends JeeslLang,
							  D extends JeeslDescription>
		extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	T getType();
	void setType(T type);
	
	List<UL> getLogs();
	void setLogs(List<UL> logs);
}