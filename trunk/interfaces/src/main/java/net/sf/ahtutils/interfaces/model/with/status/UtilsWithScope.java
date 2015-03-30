package net.sf.ahtutils.interfaces.model.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithScope<L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>>
						extends EjbWithId
{	
	SCOPE getScope();
	void setScope(SCOPE scope);
}