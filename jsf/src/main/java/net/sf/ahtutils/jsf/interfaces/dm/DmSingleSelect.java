package net.sf.ahtutils.jsf.interfaces.dm;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface DmSingleSelect <T extends EjbWithId>
{	
	void dmSingleSelected(T t);
}
