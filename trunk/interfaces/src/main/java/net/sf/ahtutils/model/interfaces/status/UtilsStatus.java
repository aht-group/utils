package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;

public interface UtilsStatus<L extends UtilsLang, D extends UtilsDescription> extends EjbRemoveable,EjbWithCode
{	
	public void setId(long id);
	public long getId();
	
	public Map<String, L> getName();
	public void setName(Map<String, L> name);
	
	public String getCode();
	public void setCode(String code);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	
	public String getImage();
	public void setImage(String image);
	
	public int getPosition();
	public void setPosition(int position);
}