package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithUtilsType<S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> extends EjbWithId
{
	S getType();
	void setType(S type);
}
