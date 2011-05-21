package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

public interface UtilsStatus
{	
	public void setId(int id);
	public int getId();
	
	public Map<String, ? extends UtilsLang> getName();
	public void setName(Map<String, ? extends UtilsLang> name);
	
	public String getCode();
	public void setCode(String code);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
}