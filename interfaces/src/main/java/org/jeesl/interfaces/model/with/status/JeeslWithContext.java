package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithContext<CTX extends JeeslStatus<CTX,?,?>> extends EjbWithId
{
	public static String attributeContext = "context";
	
	CTX getContext();
	void setContext(CTX context);
}