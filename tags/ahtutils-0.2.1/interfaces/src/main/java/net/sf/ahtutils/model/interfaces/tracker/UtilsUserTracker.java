package net.sf.ahtutils.model.interfaces.tracker;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsUserTracker<TR extends UtilsUserTracker<TR,UL,U,T,S,L>,
							  UL extends UtilsUserTrackerLog<TR,UL,U,T,S,L>,
							  U extends EjbWithId,
							  T extends UtilsStatus<L>,
							  S extends UtilsStatus<L>,
							  L extends UtilsLang>
		extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	T getType();
	void setType(T type);
	
	List<UL> getLogs();
	void setLogs(List<UL> logs);
}