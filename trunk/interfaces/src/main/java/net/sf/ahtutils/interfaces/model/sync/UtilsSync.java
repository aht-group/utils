package net.sf.ahtutils.interfaces.model.sync;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsSync<L extends UtilsLang,
							D extends UtilsDescription,
							STATUS extends UtilsStatus<STATUS,L,D>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>>
			extends EjbWithId,EjbWithCode
{
	STATUS getStatus();
	void setStatus(STATUS status);
	
	CATEGORY getCategory();
	void setCategory(CATEGORY catgory);
	
	
}