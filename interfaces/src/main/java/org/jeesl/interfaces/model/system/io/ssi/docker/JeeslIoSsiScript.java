package org.jeesl.interfaces.model.system.io.ssi.docker;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiScript <INSTANCE extends JeeslIoSsiInstance<?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{	
	public enum Attributes {entity,json}
	
	INSTANCE getInstance();
	void setInstance(INSTANCE instance);
}