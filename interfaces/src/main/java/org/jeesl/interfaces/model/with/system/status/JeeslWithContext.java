package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithContext<CTX extends JeeslStatus<?,?,CTX>> extends EjbWithId
{
	public static String attributeContext = "context";
	
	CTX getContext();
	void setContext(CTX context);
}