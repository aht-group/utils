package net.sf.ahtutils.interfaces.model.monitoring;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface UtilsMonitorData<C extends UtilsMonitorComponent<C,I,D,V>,I extends UtilsMonitorIndicator<C,I,D,V>, D extends UtilsMonitorData<C,I,D,V>,V extends UtilsMonitorValue<C,I,D,V>>
		extends EjbWithId
{
	I getIndicator();
	void setIndicator(I indicator);
}