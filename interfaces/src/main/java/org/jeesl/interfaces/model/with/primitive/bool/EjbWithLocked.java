package org.jeesl.interfaces.model.with.primitive.bool;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithLocked extends EjbWithId
{
	public Boolean getLocked();
	public void setLocked(Boolean visible);
}