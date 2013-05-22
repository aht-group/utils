package net.sf.ahtutils.jsf.interfaces.dm;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface DmSingleSelect <T extends EjbWithId>
{	
	void dmSingleSelected(T t);
}
