package org.jeesl.factory.ejb.module.workflow;

import java.util.Date;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowDelegate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowDelegateFactory<WY extends JeeslWorkflowActivity<?,?,WD,?,USER>,
										WD extends JeeslWorkflowDelegate<WY,USER>,
										USER extends JeeslUser<?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowDelegateFactory.class);
	
	final Class<WD> cDelegate;
    
	public EjbWorkflowDelegateFactory(final Class<WD> cDelegate)
	{       
        this.cDelegate = cDelegate;
	}
	    
	public WD build(WY activity, USER user)
	{
		WD ejb = null;
		try
		{
			ejb = cDelegate.newInstance();
			ejb.setActivity(activity);
			ejb.setUserRequest(user);
			ejb.setRecordRequest(new Date());
			ejb.setRemarkRequest("");
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}