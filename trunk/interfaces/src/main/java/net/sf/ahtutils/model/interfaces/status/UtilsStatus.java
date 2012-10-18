package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;

public interface UtilsStatus<L extends UtilsLang, D extends UtilsDescription> extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible
{		
	public Map<String, D> getDescription();
	public void setDescription(Map<String, D> description);
	
	public Map<String, L> getName();
	public void setName(Map<String, L> name);
	
	public String getImage();
	public void setImage(String image);
	
	public String getStyle();
	public void setStyle(String style);
}