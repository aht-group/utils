package org.jeesl.interfaces.model.marker;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbEquals <O extends EjbWithId> extends EjbWithId
{	
	boolean equalsAttributes(O other);
}