package org.jeesl.web.mbean.prototype.system.job;

import java.io.Serializable;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.JobFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public abstract class AbstractAdminJobBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FEEDBACK extends JeeslJobFeedback<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FT extends UtilsStatus<FT,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									USER extends EjbWithEmail
									>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobBean.class);
	
	protected JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob;
	protected final JobFactoryBuilder<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fbJob;
	
	protected SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiHandler<TYPE> sbhType; public SbMultiHandler<TYPE> getSbhType() {return sbhType;}
	protected final SbMultiHandler<STATUS> sbhStatus; public SbMultiHandler<STATUS> getSbhStatus() {return sbhStatus;}

	public AbstractAdminJobBean(JobFactoryBuilder<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fbJob)
	{
		super(fbJob.getClassL(),fbJob.getClassD());
		this.fbJob=fbJob;
		
		sbhStatus = new SbMultiHandler<STATUS>(fbJob.getClassStatus(),this);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fJob=fJob;
		
		sbhCategory = new SbMultiHandler<CATEGORY>(fbJob.getClassCategory(),fJob.allOrderedPositionVisible(fbJob.getClassCategory()),this);
		sbhCategory.selectAll();
		
		sbhType = new SbMultiHandler<TYPE>(fbJob.getClassType(),fJob.allOrderedPositionVisible(fbJob.getClassType()),this);
		sbhType.selectAll();
		
		sbhStatus.setList(fJob.allOrderedPositionVisible(fbJob.getClassStatus()));
	}
	
	@Override public void toggled(Class<?> c)
	{
		
	}
}