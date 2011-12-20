package net.sf.ahtutils.controller.interfaces;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;

public interface AhtTrackerFacade extends UtilsFacade
{	
	<TR extends UtilsTracker<TR,TL,T,S,L>, TL extends UtilsTrackerLog<TR,TL,T,S,L>,T extends UtilsStatus<L>, S extends UtilsStatus<L>, L extends UtilsLang>
		TR fTracker(Class<TR> trClass, T type);
}
