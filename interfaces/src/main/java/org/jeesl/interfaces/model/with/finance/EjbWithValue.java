package org.jeesl.interfaces.model.with.finance;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithValue extends EjbWithId
{
	double getValue();
	void setValue(double value);
}