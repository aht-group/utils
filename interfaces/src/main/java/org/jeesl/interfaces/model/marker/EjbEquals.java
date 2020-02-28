package org.jeesl.interfaces.model.marker;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbEquals <O extends EjbWithId> extends EjbWithId
{	
	boolean equalsAttributes(O other);
}