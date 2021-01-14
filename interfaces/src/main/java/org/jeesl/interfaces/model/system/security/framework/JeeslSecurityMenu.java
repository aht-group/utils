package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;

public interface JeeslSecurityMenu<V extends JeeslSecurityView<?,?,?,?,?,?>,
									CTX extends JeeslSecurityContext<?,?>,
									M extends JeeslSecurityMenu<V,CTX,M>>
			extends Serializable,EjbSaveable,EjbRemoveable,
						EjbWithPosition,
						EjbWithParentAttributeResolver
{
	public static final String extractId = "securityMenu";
	public static final String keyRoot = "root";
	public enum Attributes{context,parent,view}
	
	CTX getContext();
	void setContext(CTX context);
	
	M getParent();
	void setParent(M menu);
	
	V getView();
	void setView(V view);
}