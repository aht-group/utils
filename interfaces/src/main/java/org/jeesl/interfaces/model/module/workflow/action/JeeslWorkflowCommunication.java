package org.jeesl.interfaces.model.module.workflow.action;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowCommunication <WT extends JeeslWorkflowTransition<?,?,?,?,?,?>,
											MT extends JeeslIoTemplate<?,?,?,?,?,?>,
											MC extends JeeslTemplateChannel<?,?,MC,?>,
											SR extends JeeslSecurityRole<?,?,?,?,?,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
				
{
	public static enum Attributes{transition}
	
	WT getTransition();
	void setTransition(WT transition);
	
	MC getChannel();
	void setChannel(MC channel);
	
	MT getTemplate();
	void setTemplate(MT template);
	
	SR getRole();
	void setRole(SR role);
	
	RE getScope();
	void setScope(RE scope);
}