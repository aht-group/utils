package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsTracker<TR extends UtilsTracker<TR,TL,T,S,L,D>,
							  TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
							  T extends UtilsStatus<L,D>,
							  S extends UtilsStatus<L,D>,
							  L extends UtilsLang,
							  D extends UtilsDescription>
		extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	T getType();
	void setType(T type);
	
	List<TL> getLogs();
	void setLogs(List<TL> logs);
}