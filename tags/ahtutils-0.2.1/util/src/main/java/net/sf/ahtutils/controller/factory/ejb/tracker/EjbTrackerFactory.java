package net.sf.ahtutils.controller.factory.ejb.tracker;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTrackerFactory<TR extends UtilsTracker<TR,TL,T,S,L>,
			TL extends UtilsTrackerLog<TR,TL,T,S,L>,
			T extends UtilsStatus<L>,
			S extends UtilsStatus<L>,
			L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTrackerFactory.class);
	
    final Class<TR> clTracker;
	
    public static <TR extends UtilsTracker<TR,TL,T,S,L>,
	  TL extends UtilsTrackerLog<TR,TL,T,S,L>,
	  T extends UtilsStatus<L>,
	  S extends UtilsStatus<L>,
	  L extends UtilsLang> EjbTrackerFactory<TR,TL,T,S,L> createFactory(final Class<TR> clTracker)
    {
        return new EjbTrackerFactory<TR,TL,T,S,L>(clTracker);
    }
    
    public EjbTrackerFactory(final Class<TR> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public TR create(T type, long refId)
    {
    	TR ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}