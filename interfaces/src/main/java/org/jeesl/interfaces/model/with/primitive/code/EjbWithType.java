package org.jeesl.interfaces.model.with.primitive.code;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithType extends EjbWithId
{	
	public String getType();
	public void setType(String type);
}