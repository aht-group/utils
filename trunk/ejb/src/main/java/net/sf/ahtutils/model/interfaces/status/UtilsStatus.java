package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

public interface UtilsStatus<T extends UtilsLang>
{	
	public void setId(int id);
	public int getId();
	
	public Map<String, T> getName();
	public void setName(Map<String, T> name);
	
	public String getCode();
	public void setCode(String code);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
}