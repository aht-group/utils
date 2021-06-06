package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithScope<SCOPE extends JeeslStatus<?,?,SCOPE>>
						extends EjbWithId
{	
	SCOPE getScope();
	void setScope(SCOPE scope);
}