package org.jeesl.interfaces.model.with.primitive.code;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithNonUniqueCode extends EjbWithId
{
	public String getCode();
	public void setCode(String code);
}
