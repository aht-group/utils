package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithPriority<PRIORITY extends JeeslStatus<PRIORITY,?,?>> extends EjbWithId
{
	PRIORITY getPriority();
	void setPriority(PRIORITY category);
}