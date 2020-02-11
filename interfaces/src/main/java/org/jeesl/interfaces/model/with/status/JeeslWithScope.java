package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithScope<SCOPE extends JeeslStatus<SCOPE,?,?>>
						extends EjbWithId
{	
	SCOPE getScope();
	void setScope(SCOPE scope);
}