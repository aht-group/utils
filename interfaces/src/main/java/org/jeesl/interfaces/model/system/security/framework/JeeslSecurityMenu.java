package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

public interface JeeslSecurityMenu<V extends JeeslSecurityView<?,?,?,?,?,?>,
								   M extends JeeslSecurityMenu<V,M>>
			extends Serializable,EjbSaveable,EjbRemoveable,
						EjbWithPosition,
						EjbWithParentAttributeResolver
{
	public static final String extractId = "securityMenu";
	public static final String keyRoot = "root";
	public enum Attributes{parent,view}
	
	M getParent();
	void setParent(M menu);
	
	V getView();
	void setView(V view);
}