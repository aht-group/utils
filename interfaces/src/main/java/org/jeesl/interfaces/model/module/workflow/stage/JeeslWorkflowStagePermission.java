package org.jeesl.interfaces.model.module.workflow.stage;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowStagePermission <AS extends JeeslWorkflowStage<?,?,?,?,?,?,?>,
									APT extends JeeslWorkflowPermissionType<?,?,APT,?>,
									WML extends JeeslWorkflowModificationLevel<?,?,WML,?>,
									SR extends JeeslSecurityRole<?,?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
{
	public static enum Attributes{stage}
	
	AS getStage();
	void setStage(AS stage);
	
	APT getType();
	void setType(APT type);
	
	SR getRole();
	void setRole(SR role);
	
	WML getModificationLevel();
	void setModificationLevel(WML modificationLevel);
}