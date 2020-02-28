package org.jeesl.interfaces.model.system.security.with;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslSecurityWithViews <V extends JeeslSecurityView<?,?,?,?,?,?>>
										extends EjbWithId
{
	public List<V> getViews();
	public void setViews(List<V> views);
}