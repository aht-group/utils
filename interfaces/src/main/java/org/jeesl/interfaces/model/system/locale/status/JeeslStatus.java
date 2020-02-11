package org.jeesl.interfaces.model.system.locale.status;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.graphic.EjbWithImage;
import org.jeesl.interfaces.model.with.graphic.EjbWithImageAlt;
import org.jeesl.interfaces.model.with.parent.EjbWithParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslStatus<S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription>
			extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible,EjbWithImage,EjbWithImageAlt,
						EjbWithLangDescription<L,D>,EjbWithParent
{	
	enum EjbAttributes{code,parent}
	
	String getStyle();
	void setStyle(String style);
	
	String getSymbol();
	void setSymbol(String symbol);
}