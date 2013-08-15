package net.sf.ahtutils.interfaces.model.sensor;

import net.sf.ahtutils.xml.monitoring.Data;
import net.sf.ahtutils.xml.monitoring.Indicator;
import net.sf.ahtutils.xml.monitoring.Observer;

public interface Sensor extends SensorRead
{	
	public double getRaw();

	public Data getData();
	
	public Indicator getIndicator();
	public Observer getObserver();
}
