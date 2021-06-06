package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface UtilsUserTracker<TR extends UtilsUserTracker<TR,UL,U,T,S,L,D>,
							  UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L,D>,
							  U extends EjbWithId,
							  T extends JeeslStatus<L,D,S>,
							  S extends JeeslStatus<L,D,S>,
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