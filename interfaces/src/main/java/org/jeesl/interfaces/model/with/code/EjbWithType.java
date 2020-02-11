package org.jeesl.interfaces.model.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithType extends EjbWithId
{	
	public String getType();
	public void setType(String type);
}