package net.sf.ahtutils.model.interfaces.status;

import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStatusDefinition<S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription, DEF extends EjbWithId> extends EjbWithId,EjbRemoveable
{	
	public DEF getDefinition();
	public void setDefinition(DEF definition);
	
	public S getAllowed();
	public void setAllowed(S allowed);
	
	public int getOrderNr();
	public void setOrderNr(int orderNr);
	
	
}