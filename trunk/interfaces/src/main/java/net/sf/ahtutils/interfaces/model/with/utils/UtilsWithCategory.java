package net.sf.ahtutils.interfaces.model.with.utils;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithCategory<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends UtilsStatus<CATEGORY,L,D>>
						extends EjbWithId
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY catgory);
}