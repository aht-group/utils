package org.jeesl.factory.ejb.module.workflow;

import java.util.Date;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowActivityFactory<WT extends JeeslWorkflowTransition<?,?,?,?,?,?>,
										WF extends JeeslWorkflow<?,?,WY,USER>,
										WY extends JeeslWorkflowActivity<WT,WF,?,?,USER>,
										USER extends JeeslUser<?>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowActivityFactory.class);
	
	final Class<WY> cActivity;
    
	public EjbWorkflowActivityFactory(final Class<WY> cActivity)
	{       
        this.cActivity = cActivity;
	}
	    
	public WY build(WF workflow, WT transition, USER user)
	{
		return build(workflow,transition,user,new Date());
	}
	
	public WY build(WF workflow, WT transition, USER user, Date date)
	{
		return build(workflow,transition,user,new Date(),null);
	}
	public WY build(WF workflow, WT transition, USER user, Date date, String remark)
	{
		WY ejb = null;
		try
		{
			ejb = cActivity.newInstance();
			ejb.setWorkflow(workflow);
			ejb.setTransition(transition);
			ejb.setRecord(date);
			ejb.setUser(user);
			ejb.setRemark(remark);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}