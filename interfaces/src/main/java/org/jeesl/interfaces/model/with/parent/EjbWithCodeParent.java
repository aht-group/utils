package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithCodeParent <P extends EjbWithCode> extends EjbWithId
{
	P getParent();
	void setParent(P parent);
}