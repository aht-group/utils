package net.sf.ahtutils.interfaces.model.with.utils;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithStatus<L extends UtilsLang,D extends UtilsDescription,STATUS extends UtilsStatus<STATUS,L,D>>
						extends EjbWithId
{	
	//move to package status
	
	STATUS getStatus();
	void setStatus(STATUS status);
}