package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflow <WP extends JeeslWorkflowProcess<?,?,?,WS>,
										WS extends JeeslWorkflowStage<?,?,WP,?,?,?,?>,
										WY extends JeeslWorkflowActivity<?,?,?,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver
{
	public static enum Attributes{process,currentStage,lastActivity}
	
	WP getProcess();
	void setProcess(WP process);
	
	WS getCurrentStage();
	void setCurrentStage(WS currentStage);
	
	List<WY> getActivities();
	void setActivities(List<WY> activities);
	
	WY getLastActivity();
	void setLastActivity(WY lastActivity);
}