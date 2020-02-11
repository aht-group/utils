package org.jeesl.factory.ejb.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeCriteriaFactory<L extends JeeslLang, D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends JeeslStatus<TYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeCriteriaFactory.class);
	
	private final Class<CRITERIA> cCriteria;
    
	public EjbAttributeCriteriaFactory(final Class<CRITERIA> cCriteria)
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