package net.sf.ahtutils.model.interfaces.link;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.date.EjbWithValidUntil;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsLink<S extends UtilsStatus<L>, L extends UtilsLang> extends EjbWithId,EjbWithValidUntil
{
	long getRefId();
	void setRefId(long refId);
	
	S getType();
	void setType(S type);
	
	String getToken();
	void setToken(String token);
}