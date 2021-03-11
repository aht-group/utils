package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeGroupFactory<L extends JeeslLang, D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE,?>,
										TYPE extends JeeslStatus<TYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTargetTimeGroupFactory.class);
	
	private final Class<CRITERIA> cCriteria;
    
	public EjbLfTargetTimeGroupFactory(final Class<CRITERIA> cCriteria)
	{       
        this.cCriteria = cCriteria;
	}
    
	public CRITERIA build(CATEGORY category, TYPE type, long refId)
	{
		CRITERIA ejb = null;
		try
		{
			ejb = cCriteria.newInstance();
			ejb.setRefId(refId);
			ejb.setCategory(category);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}