package org.jeesl.factory.ejb.module.log;

import java.util.Date;

import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogConfidentiality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbLogItemFactory<LOG extends JeeslLogBook<?,ITEM>,
								ITEM extends JeeslLogItem<?,?,?,?,LOG,IMPACT,CONF,USER>,
								IMPACT extends JeeslLogImpact<?,?,IMPACT,?>,
								CONF extends JeeslLogConfidentiality<?,?,CONF,?>,
								USER extends EjbWithId
								>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogItemFactory.class);
	
	private final Class<ITEM> cItem;
	private final Class<IMPACT> cImpact;
	private final Class<USER> cUser;
    
	public EjbLogItemFactory(final Class<ITEM> cItem, final Class<IMPACT> cImpact, final Class<USER> cUser)
	{  
        this.cItem = cItem;
        this.cImpact = cImpact;
        this.cUser = cUser;
	}
	    
	public ITEM build(USER user, IMPACT impact)
	{
		ITEM ejb = null;
		try
		{
			ejb = cItem.newInstance();
			ejb.setRecord(new Date());
			ejb.setAuthor(user);
			ejb.setImpact(impact);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void lazy(UtilsFacade facade, ITEM item)
	{
		if(item.getAuthor()!=null) {item.setAuthor(facade.find(cUser,item.getAuthor()));}
		if(item.getImpact()!=null) {item.setImpact(facade.find(cImpact,item.getImpact()));}
	}
}