package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithStatus<STATUS extends JeeslStatus<?,?,STATUS>> extends EjbWithId
{
	public static String attribute = "status";
	
	STATUS getStatus();
	void setStatus(STATUS status);
}