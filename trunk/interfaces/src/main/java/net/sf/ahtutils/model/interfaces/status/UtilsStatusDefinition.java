package net.sf.ahtutils.model.interfaces.status;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface UtilsStatusDefinition<S extends UtilsStatus<L>, L extends UtilsLang, D extends EjbWithId> extends EjbWithId,EjbRemoveable
{	
	public D getDefinition();
	public void setDefinition(D definition);
	
	public S getAllowed();
	public void setAllowed(S allowed);
	
	public int getOrderNr();
	public void setOrderNr(int orderNr);
	
	
}