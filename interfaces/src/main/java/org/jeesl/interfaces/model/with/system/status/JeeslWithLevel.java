package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithLevel<LEVEL extends JeeslStatus<LEVEL,?,?>> extends EjbWithId
{
	public static String attributeLevel = "level";
	
	LEVEL getLevel();
	void setLevel(LEVEL level);
}