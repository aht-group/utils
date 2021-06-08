package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.value.JeeslLfValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfMonitoringFactory<LFM extends JeeslLfValue<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfMonitoringFactory.class);

	private final Class<LFM> cLfMonitiring;

	public EjbLfMonitoringFactory(final Class<LFM> cLfMonitiring)
	{
		this.cLfMonitiring =  cLfMonitiring;
	}

	public LFM build()
	{
		LFM ejb = null;
		try
		{
			ejb = cLfMonitiring.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}