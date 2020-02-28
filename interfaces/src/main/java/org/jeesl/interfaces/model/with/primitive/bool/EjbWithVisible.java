package org.jeesl.interfaces.model.with.primitive.bool;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithVisible extends EjbWithId
{
	public boolean isVisible();
	public void setVisible(boolean visible);
}