package net.sf.ahtutils.interfaces.model.status;

import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithImage;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;

public interface UtilsStatus<S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
			extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible,EjbWithImage,EjbWithLangDescription<L,D>
{					
	public String getStyle();
	public void setStyle(String style);
	
	String getImageAlt();
	void setImageAlt(String image);
	
	String getSymbol();
	void setSymbol(String symbol);
	
	public <P extends EjbWithCode> P getParent();
	public <P extends EjbWithCode> void setParent(P parent);
}