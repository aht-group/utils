package org.jeesl.factory.ejb.system.job;

import org.jeesl.interfaces.model.system.job.JeeslJobCategory;
import org.jeesl.interfaces.model.system.job.JeeslJobPriority;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.interfaces.model.system.job.JeeslJobType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbJobTemplateFactory <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY,?>,
									CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
									TYPE extends JeeslJobType<L,D,TYPE,?>,
									PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbJobTemplateFactory.class);
	
	private final Class<TEMPLATE> cTemplate;

	public EjbJobTemplateFactory(final Class<TEMPLATE> cTemplate)
	{
        this.cTemplate = cTemplate;
	}
 
	public TEMPLATE build(CATEGORY category, TYPE type)
	{
		TEMPLATE ejb = null;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setCategory(category);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}