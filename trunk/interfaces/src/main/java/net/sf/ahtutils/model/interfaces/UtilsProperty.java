package net.sf.ahtutils.model.interfaces;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsProperty extends EjbWithId
{
	public String getKey();
	public void setKey(String key);
	
	public String getValue();
	public void setValue(String value);
}
