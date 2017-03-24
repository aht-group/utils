package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminJobQueueBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS>,
									FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
					extends AbstractAdminJobBean<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobQueueBean.class);
	
	private List<JOB> jobs; public List<JOB> getJobs() {return jobs;}
	
	private JOB job; public JOB getJob() {return job;} public void setJob(JOB job) {this.job = job;}
	
	protected SbMultiHandler<STATUS> sbhStatus; public SbMultiHandler<STATUS> getSbhStatus() {return sbhStatus;}
	 
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus)
	{
		super.initSuper(langs,bMessage,fJob,cLang,cDescription,cTemplate,cCategory,cType,cJob,cStatus);
		
		try
		{
			sbhStatus = new SbMultiHandler<STATUS>(cStatus,fJob.allOrderedPositionVisible(cStatus),this);
			sbhStatus.select(fJob.fByCode(cStatus,JeeslJob.Status.queue));
			sbhStatus.select(fJob.fByCode(cStatus,JeeslJob.Status.working));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
			logger.info(AbstractLogMessage.multiStatus(cStatus,sbhStatus.getSelected(),sbhStatus.getList()));
		}
	}
	
	@Override public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		if(cCategory.isAssignableFrom(c)){logger.info(cCategory.getName());}
		else if(cType.isAssignableFrom(c)){logger.info(cType.getName());}
		else if(cStatus.isAssignableFrom(c)){logger.info(cStatus.getName());}
		reloadJobs();
		clear(true);
	}
	
	private void clear(boolean clearJob)
	{
		if(clearJob){job=null;}
	}
	
	protected void reloadJobs()
	{
		jobs = fJob.fJobs(sbhCategory.getSelected(),sbhType.getSelected(),sbhStatus.getSelected());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cJob,jobs));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectJob()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(job));}
	}
}