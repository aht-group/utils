package org.jeesl.interfaces.model.module.workflow.stage;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.graphic.EjbWithGraphic;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslWorkflowStage <L extends JeeslLang, D extends JeeslDescription,
									WP extends JeeslWorkflowProcess<L,D,?,?>,
									WST extends JeeslWorkflowStageType<L,D,WST,?>,
									WSP extends JeeslWorkflowStagePermission<?,?,?,?>,
									WT extends JeeslWorkflowTransition<L,D,?,?,?,?>,
									G extends JeeslGraphic<L,D,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithCode,EjbWithPosition,EjbWithParentAttributeResolver,
				EjbWithLang<L>,EjbWithDescription<D>,EjbWithGraphic<G>
{
	public static enum Attributes{process}
	
	WP getProcess();
	void setProcess(WP process);
	
	WST getType();
	void setType(WST type);
	
	List<WT> getTransitions();
	void setTransitions(List<WT> transitions);
	
	List<WSP> getPermissions();
	void setPermissions(List<WSP> permissions);
}