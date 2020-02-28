package org.jeesl.interfaces.model.with.finance;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithValue extends EjbWithId
{
	double getValue();
	void setValue(double value);
}