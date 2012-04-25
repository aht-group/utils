package net.sf.ahtutils.controller.factory.ejb.link;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.link.UtilsLink;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLinkFactory<LI extends UtilsLink<T,L>,T extends UtilsStatus<L>,L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLinkFactory.class);
	
    final Class<LI> clLink;
	
    public static <LI extends UtilsLink<T,L>,T extends UtilsStatus<L>,L extends UtilsLang>
    EjbLinkFactory<LI,T,L> createFactory(final Class<LI> clLink)
  {
      return new EjbLinkFactory<LI,T,L>(clLink);
  }
    
    public EjbLinkFactory(final Class<LI> clLink)
    {
        this.clLink = clLink;
    } 
    
    public LI create(T type, long refId, String code, Date validUntil)
    {
    	LI ejb = null;
    	
    	try
    	{
			ejb = clLink.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
			ejb.setCode(code);
			ejb.setValidUntil(validUntil);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}