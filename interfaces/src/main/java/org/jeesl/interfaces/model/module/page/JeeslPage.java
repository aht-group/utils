package org.jeesl.interfaces.model.module.page;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

public interface JeeslPage<V extends JeeslSecurityView<?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbWithPosition
{
	V getView();
	void setView(V view);
}