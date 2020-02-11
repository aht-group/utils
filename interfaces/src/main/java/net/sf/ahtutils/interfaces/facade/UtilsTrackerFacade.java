package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.interfaces.model.system.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTrackerFacade extends JeeslFacade
{	
	<TR extends UtilsTracker<TR,TL,T,S,L,D>, TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,T extends JeeslStatus<T,L,D>, S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription>
		List<TR> allTrackerForType(Class<TR> clTracker, Class<T> clType, T type);
	
	<TR extends UtilsTracker<TR,TL,T,S,L,D>, TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,T extends JeeslStatus<T,L,D>, S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription>
		TR fTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws JeeslNotFoundException;
	
	<TR extends UtilsMailTracker<T,L,U,D>,T extends JeeslStatus<T,L,D>, L extends JeeslLang, U extends EjbWithId, D extends JeeslDescription> 
		List<TR> fMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws JeeslNotFoundException;
	
	<TR extends UtilsMailTracker<T,L,U,D>,T extends JeeslStatus<T,L,D>, L extends JeeslLang, U extends EjbWithId, D extends JeeslDescription> 
		TR fLastMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws JeeslNotFoundException;
}