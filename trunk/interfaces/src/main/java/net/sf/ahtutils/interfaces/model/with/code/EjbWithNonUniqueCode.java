package net.sf.ahtutils.interfaces.model.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithNonUniqueCode extends EjbWithId
{
	public String getCode();
	public void setCode(String code);
}
