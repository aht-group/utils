package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslIoCmsVisiblity
		extends Serializable,EjbWithId,
				EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithVisible
{	
		
}