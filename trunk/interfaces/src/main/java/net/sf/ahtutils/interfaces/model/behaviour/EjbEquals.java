package net.sf.ahtutils.interfaces.model.behaviour;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbEquals <O extends EjbWithId> extends EjbWithId
{	
	boolean equalsAttributes(O other);
}