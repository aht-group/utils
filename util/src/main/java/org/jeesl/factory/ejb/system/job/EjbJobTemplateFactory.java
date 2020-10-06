package org.jeesl.factory.ejb.system.job;

import org.jeesl.factory.builder.system.JobFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.job.JeeslJobCategory;
import org.jeesl.interfaces.model.system.job.JeeslJobExpiration;
import org.jeesl.interfaces.model.system.job.JeeslJobPriority;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.interfaces.model.system.job.JeeslJobType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbJobTemplateFactory <L extends JeeslLang,D extends JeeslDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY,EXPIRE>,
									CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
									TYPE extends JeeslJobType<L,D,TYPE,?>,
									EXPIRE extends JeeslJobExpiration<L,D,EXPIRE,?>,
									PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbJobTemplateFactory.class);
	
	private final JobFactoryBuilder<?,?,TEMPLATE,CATEGORY,TYPE,EXPIRE,?,PRIORITY,?,?,?,?,?,?> fbJob;

	public EjbJobTemplateFactory(JobFactoryBuilder<?,?,TEMPLATE,CATEGORY,TYPE,EXPIRE,?,PRIORITY,?,?,?,?,?,?> fbJob)
	{
        this.fbJob = fbJob;
	}
 
	public TEMPLATE build(CATEGORY category, TYPE type)
	{
		TEMPLATE ejb = null;
		try
		{
			ejb = fbJob.getClassTemplate().newInstance();
			ejb.setCategory(category);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void converter(JeeslFacade facade, TEMPLATE template)
	{
		if(template.getCategory()!=null) {template.setCategory(facade.find(fbJob.getClassCategory(),template.getCategory()));}
		if(template.getType()!=null) {template.setType(facade.find(fbJob.getClassType(),template.getType()));}
		if(template.getPriority()!=null) {template.setPriority(facade.find(fbJob.getClassPriority(),template.getPriority()));}
		if(template.getExpiration()!=null) {template.setExpiration(facade.find(fbJob.getClassExpire(),template.getExpiration()));}
	}
}