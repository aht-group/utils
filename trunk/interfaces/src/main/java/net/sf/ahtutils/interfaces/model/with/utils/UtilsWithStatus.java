package net.sf.ahtutils.interfaces.model.with.utils;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithStatus<L extends UtilsLang,D extends UtilsDescription,STATUS extends UtilsStatus<STATUS,L,D>>
						extends EjbWithId
{	
	STATUS getStatus();
	void setStatus(STATUS status);
}