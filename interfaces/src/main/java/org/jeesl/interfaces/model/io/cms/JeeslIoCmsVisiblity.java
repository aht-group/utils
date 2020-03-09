package org.jeesl.interfaces.model.io.cms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.bool.EjbWithVisible;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslIoCmsVisiblity
		extends Serializable,EjbWithId,
				EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithVisible
{	
		
}