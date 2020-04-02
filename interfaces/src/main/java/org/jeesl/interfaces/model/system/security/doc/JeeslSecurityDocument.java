package org.jeesl.interfaces.model.system.security.doc;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;

public interface JeeslSecurityDocument<V extends JeeslSecurityView<?,?,?,?,?,?>,
										DOC extends JeeslIoCms<?,?,?,SEC,?>,
										SEC extends JeeslIoCmsSection<?,SEC>>
			extends Serializable,EjbSaveable,EjbWithPositionVisible,EjbWithParentAttributeResolver
{
	public enum Attributes{view}
	
	V getView();
	void setView(V view);
	
	DOC getDocument();
	void setDocument(DOC document);
	
	SEC getSection();
	void setSection(SEC section);
}