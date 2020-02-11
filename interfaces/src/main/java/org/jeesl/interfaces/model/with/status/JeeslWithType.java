package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithType<TYPE extends JeeslStatus<TYPE,?,?>>extends EjbWithId
{
	public static String attributeType = "type";
	
	TYPE getType();
	void setType(TYPE type);
}