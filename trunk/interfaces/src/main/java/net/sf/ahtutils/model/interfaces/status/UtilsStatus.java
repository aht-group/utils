package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;

public interface UtilsStatus<T extends UtilsLang> extends EjbRemoveable,EjbWithCode
{	
	public void setId(long id);
	public long getId();
	
	public Map<String, T> getName();
	public void setName(Map<String, T> name);
	
	public String getCode();
	public void setCode(String code);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	
	public String getImage();
	public void setImage(String image);
}