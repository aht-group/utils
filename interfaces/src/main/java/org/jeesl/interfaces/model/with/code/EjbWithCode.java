package org.jeesl.interfaces.model.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithCode extends EjbWithId
{	
	public String getCode();
	public void setCode(String code);
}