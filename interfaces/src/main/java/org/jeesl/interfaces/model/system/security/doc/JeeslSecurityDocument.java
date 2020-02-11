package org.jeesl.interfaces.model.system.security.doc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

public interface JeeslSecurityDocument<V extends JeeslSecurityView<?,?,?,?,?,?>,
											S extends JeeslIoCmsSection<?,S>>
			extends Serializable,EjbSaveable,EjbWithPositionVisible
{	
	V getView();
	void setView(V view);
	
	S getSection();
	void setSection(S section);
}