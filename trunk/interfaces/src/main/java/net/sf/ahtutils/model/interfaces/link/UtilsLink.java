package net.sf.ahtutils.model.interfaces.link;

import net.sf.ahtutils.interfaces.model.date.EjbWithValidUntil;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsLink<S extends UtilsStatus<S,L,D>,
							L extends UtilsLang,
							D extends UtilsDescription>
						extends EjbWithId,EjbWithCode,EjbWithValidUntil
{
	long getRefId();
	void setRefId(long refId);
	
	S getType();
	void setType(S type);
}