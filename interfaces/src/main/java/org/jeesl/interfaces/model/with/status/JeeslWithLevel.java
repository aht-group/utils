package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithLevel<LEVEL extends JeeslStatus<LEVEL,?,?>> extends EjbWithId
{
	public static String attributeLevel = "level";
	
	LEVEL getLevel();
	void setLevel(LEVEL level);
}