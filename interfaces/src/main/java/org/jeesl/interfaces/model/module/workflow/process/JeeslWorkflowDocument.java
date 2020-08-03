package org.jeesl.interfaces.model.module.workflow.process;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslWorkflowDocument <L extends JeeslLang, D extends JeeslDescription,
									WP extends JeeslWorkflowProcess<L,D,?,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithNonUniqueCode,EjbWithPosition,EjbWithParentAttributeResolver,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Attributes{process,code}
	public enum Labels{listOf}
	
	WP getProcess();
	void setProcess(WP process);
}