package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithType<TYPE extends JeeslStatus<TYPE,?,?>>extends EjbWithId
{
	public static String attributeType = "type";
	
	TYPE getType();
	void setType(TYPE type);
}