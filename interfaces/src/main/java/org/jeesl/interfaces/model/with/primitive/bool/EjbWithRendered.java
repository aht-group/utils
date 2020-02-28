package org.jeesl.interfaces.model.with.primitive.bool;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithRendered extends EjbWithId
{
	public static String attributeRendered = "rendered";
	
	public boolean isRendered();
	public void setRendered(boolean rendered);
}