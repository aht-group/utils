package org.jeesl.interfaces.model.module.workflow.process;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.status.JeeslWithContext;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslWorkflowProcess <L extends JeeslLang, D extends JeeslDescription,
									WX extends JeeslWorkflowContext<L,D,WX,?>,
									WS extends JeeslWorkflowStage<L,D,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithCode,EjbWithPosition,
				JeeslWithContext<WX>,EjbWithLang<L>,EjbWithDescription<D>
{
	List<WS> getStages();
	void setStages(List<WS> stages);
}