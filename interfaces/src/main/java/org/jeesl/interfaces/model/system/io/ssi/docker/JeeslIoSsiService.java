package org.jeesl.interfaces.model.system.io.ssi.docker;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiService <INSTANCE extends JeeslIoSsiInstance<?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{	
	public enum Attributes {entity,json}
	
	INSTANCE getInstance();
	void setInstance(INSTANCE instance);
}