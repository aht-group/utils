package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithParent extends EjbWithId
{
	public <P extends EjbWithCode> P getParent();
	public <P extends EjbWithCode> void setParent(P parent);
}