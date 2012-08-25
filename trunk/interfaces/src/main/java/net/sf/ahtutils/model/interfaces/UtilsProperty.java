package net.sf.ahtutils.model.interfaces;

public interface UtilsProperty extends EjbWithId
{
	public String getKey();
	public void setKey(String key);
	
	public String getValue();
	public void setValue(String value);
}
