package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowLink <WF extends JeeslWorkflow<?,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithRefId,EjbWithParentAttributeResolver
{
	public static enum Attributes{refId,workflow,entity}
	
	WF getWorkflow();
	void setWorkflow(WF workflow);
	
	RE getEntity();
	void setEntity(RE entity);
}