package org.jeesl.factory.ejb.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeSetFactory<L extends JeeslLang, D extends JeeslDescription,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<?,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeSetFactory.class);
	
	private final Class<SET> cSet;
    
	public EjbAttributeSetFactory(final Class<SET> cSet)
	{       
        this.cSet = cSet;
	}
    
	public SET build(CATEGORY category, long refId)
	{
		SET ejb = null;
		try
		{
			ejb = cSet.newInstance();
			ejb.setRefId(refId);
			ejb.setCategory(category);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}