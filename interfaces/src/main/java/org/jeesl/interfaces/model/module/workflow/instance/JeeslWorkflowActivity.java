package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslWorkflowActivity <AT extends JeeslWorkflowTransition<?,?,?,?,?,?>,
										WF extends JeeslWorkflow<?,?,?>,
										FRC extends JeeslFileContainer<?,?>,
										USER extends JeeslUser<?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver,
				EjbWithRecord,JeeslWithFileRepositoryContainer<FRC>
{
	public static enum Attributes{workflow}
	
	WF getWorkflow();
	void setWorkflow(WF workflow);
	
	AT getTransition();
	void setTransition(AT transition);
	
	USER getUser();
	void setUser(USER user);
	
	String getRemark();
	void setRemark(String remark);
	
	String getScreenSignature();
	void setScreenSignature(String screenSignature);
}