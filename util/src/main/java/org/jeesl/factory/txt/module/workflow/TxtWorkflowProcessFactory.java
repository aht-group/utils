package org.jeesl.factory.txt.module.workflow;

import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtWorkflowProcessFactory<WP extends JeeslWorkflowProcess<?,?,?,WS>,
										WS extends JeeslWorkflowStage<?,?,WP,?,?,WT,?>,
										WT extends JeeslWorkflowTransition<?,?,WS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtWorkflowProcessFactory.class);
	
	private final String localeCode;
	
	private JeeslFacade fWf;
	private WorkflowFactoryBuilder<?,?,?,WP,WS,?,?,?,?,WT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbWf;
	
	public TxtWorkflowProcessFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public void lazy(JeeslFacade fWf,WorkflowFactoryBuilder<?,?,?,WP,WS,?,?,?,?,WT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbWf)
	{
		this.fWf=fWf;
		this.fbWf=fbWf;
	}
	
	public void debug()
	{
		if(fWf!=null)
		{
			for(WP p : fWf.all(fbWf.getClassProcess()))
			{
				this.debug(p);
			}
		}
		
	}
	
	public void debug(WP process)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(process.getId()+" "+process.getName().get(localeCode).getLang());
		sb.append(" (").append(process.getContext().getName().get(localeCode).getLang()).append(")");
		logger.info(sb.toString());
		
		if(fWf!=null)
		{
			for(WS stage : fWf.allForParent(fbWf.getClassStage(),process))
			{
				StringBuilder sbStage = new StringBuilder();
				sbStage.append("\t");
				sbStage.append(stage.getId()).append(" ").append(stage.getName().get(localeCode).getLang());
				logger.info(sbStage.toString());
				for(WT transition : fWf.allForParent(fbWf.getClassTransition(),stage))
				{
					StringBuilder sbTransition = new StringBuilder();
					sbTransition.append("\t\t");
					sbTransition.append(transition.getId()).append(" ").append(transition.getName().get(localeCode).getLang());
					logger.info(sbTransition.toString());
				}
			}
		}
		
	}
}
