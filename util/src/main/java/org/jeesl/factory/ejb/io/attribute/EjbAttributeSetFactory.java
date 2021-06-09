package org.jeesl.factory.ejb.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCategory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeSetFactory<L extends JeeslLang, D extends JeeslDescription,
									CAT extends JeeslAttributeCategory<L,D,?,CAT,?>,
									CATEGORY extends JeeslStatus<L,D,CATEGORY>,
									SET extends JeeslAttributeSet<L,D,?,CAT,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<?,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeSetFactory.class);
	
	private final Class<SET> cSet;
    
	public EjbAttributeSetFactory(final Class<SET> cSet)
	{       
        this.cSet = cSet;
	}
    
	public SET build(CATEGORY category, Long refId)
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
	
	public SET clone(SET source, String codeSuffix)
	{
		SET clone = this.build(source.getCategory(),source.getRefId());
		clone.setCode(source.getCode()+codeSuffix);
		return clone;
	}
}