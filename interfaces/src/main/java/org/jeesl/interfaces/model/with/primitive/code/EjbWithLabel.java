package org.jeesl.interfaces.model.with.primitive.code;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithLabel extends EjbWithId
{	
	public String getLabel();
	public void setLabel(String label);
}