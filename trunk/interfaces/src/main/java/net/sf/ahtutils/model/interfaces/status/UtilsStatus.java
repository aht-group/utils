package net.sf.ahtutils.model.interfaces.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithImage;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;

public interface UtilsStatus<L extends UtilsLang, D extends UtilsDescription>
			extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible,EjbWithImage
{		
	public Map<String, D> getDescription();
	public void setDescription(Map<String, D> description);
	
	public Map<String, L> getName();
	public void setName(Map<String, L> name);
		
	public String getStyle();
	public void setStyle(String style);
	
	String getImageAlt();
	void setImageAlt(String image);
}