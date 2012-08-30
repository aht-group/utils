package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;

public interface UtilsTrackerFacade extends UtilsFacade
{	
	<TR extends UtilsTracker<TR,TL,T,S,L,D>, TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,T extends UtilsStatus<L,D>, S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription>
		List<TR> allTrackerForType(Class<TR> clTracker, Class<T> clType, T type);
	
	<TR extends UtilsTracker<TR,TL,T,S,L,D>, TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,T extends UtilsStatus<L,D>, S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription>
		TR fTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException;
	
	<TR extends UtilsMailTracker<T,L,U,D>,T extends UtilsStatus<L,D>, L extends UtilsLang, U extends EjbWithId, D extends UtilsDescription> 
		List<TR> fMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException;
	
	<TR extends UtilsMailTracker<T,L,U,D>,T extends UtilsStatus<L,D>, L extends UtilsLang, U extends EjbWithId, D extends UtilsDescription> 
		TR fLastMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException;
}
