package org.jeesl.factory.sql.module.workflow;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlWorkflowFactory<WL extends JeeslWorkflowLink<WF,?>,
								WF extends JeeslWorkflow<?,?,WY,?>,
								WY extends JeeslWorkflowActivity<?,WF,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(SqlWorkflowFactory.class);
	
	private final Class<WL> cWl;
	private final Class<WF> cWf;
	private final Class<WY> cWy;
	
	public SqlWorkflowFactory(Class<WL> cWl, Class<WF> cWf, Class<WY> cWy)
	{
		this.cWl=cWl;
		this.cWf=cWf;
		this.cWy=cWy;
	}
	
	public static <WS extends JeeslWorkflowStage<?,?,?,?,?,?,?>,
					WF extends JeeslWorkflow<?,WS,?,?>>
				String updateCurrentStage(Class<WF> c, WF workflow, WS stage)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		try{sb.append(ReflectionUtil.toTable(c));} catch (JeeslNotFoundException e){e.printStackTrace();}
		sb.append(" SET ").append(JeeslWorkflow.Attributes.currentStage).append("_id=").append(stage.getId());
		sb.append(" WHERE id=").append(workflow.getId());
		sb.append(";");
		return sb.toString();
	}
	
	public static <WF extends JeeslWorkflow<?,?,?,?>> String unsetLastActivity(Class<WF> c, WF workflow)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		try{sb.append(ReflectionUtil.toTable(c));} catch (JeeslNotFoundException e){e.printStackTrace();}
		sb.append(" SET ").append(JeeslWorkflow.Attributes.lastActivity).append("_id=").append("NULL");
		sb.append(" WHERE id=").append(workflow.getId());
		sb.append(";");
		return sb.toString();
	}
	
	public static <WF extends JeeslWorkflow<?,?,?,?>> String delete(Class<WF> c, WF workflow)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		try {sb.append(ReflectionUtil.toTable(c));} catch (JeeslNotFoundException e){e.printStackTrace();}
		sb.append(" WHERE id=").append(workflow.getId());
		sb.append(";");
		return sb.toString();
	}
	
	public void delete(WL link, List<WY> activities)
	{
		System.out.println("BEGIN;");
		System.out.println(SqlWorkflowFactory.unsetLastActivity(cWf,link.getWorkflow()));
		for(WY activity : activities) {System.out.println(SqlWorkflowActivityFactory.delete(cWy,activity));}
		
		System.out.println(SqlWorkflowLinkFactory.delete(cWl,link));
		System.out.println(SqlWorkflowFactory.delete(cWf,link.getWorkflow()));
		
		System.out.println("COMMIT;");
	}
}