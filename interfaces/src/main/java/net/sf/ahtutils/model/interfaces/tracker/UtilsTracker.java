package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface UtilsTracker<TR extends UtilsTracker<TR,TL,T,S,L,D>,
							  TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
							  T extends JeeslStatus<L,D,T>,
							  S extends JeeslStatus<L,D,S>,
							  L extends JeeslLang,
							  D extends JeeslDescription>
		extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	T getType();
	void setType(T type);
	
	List<TL> getLogs();
	void setLogs(List<TL> logs);
}