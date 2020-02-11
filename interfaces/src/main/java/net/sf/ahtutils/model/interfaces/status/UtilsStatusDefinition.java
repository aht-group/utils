package net.sf.ahtutils.model.interfaces.status;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStatusDefinition<S extends JeeslStatus<S,L,D>,
										L extends JeeslLang,
										D extends JeeslDescription,
										DEF extends EjbWithId> extends EjbWithId,EjbRemoveable
{	
	public DEF getDefinition();
	public void setDefinition(DEF definition);
	
	public S getAllowed();
	public void setAllowed(S allowed);
	
	public int getOrderNr();
	public void setOrderNr(int orderNr);
	
	
}