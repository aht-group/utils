package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsUserTracker<TR extends UtilsUserTracker<TR,UL,U,T,S,L,D>,
							  UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L,D>,
							  U extends EjbWithId,
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
	
	List<UL> getLogs();
	void setLogs(List<UL> logs);
}