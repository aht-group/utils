package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithStatus<STATUS extends JeeslStatus<STATUS,?,?>> extends EjbWithId
{
	public static String attribute = "status";
	
	STATUS getStatus();
	void setStatus(STATUS status);
}