package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.value.JeeslLfValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfIndicatorValueFactory<LFV extends JeeslLfValue<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfIndicatorValueFactory.class);

	private final Class<LFV> cLfValue;

	public EjbLfIndicatorValueFactory(final Class<LFV> cLfValue)
	{
		this.cLfValue =  cLfValue;
	}

	public LFV build()
	{
		LFV ejb = null;
		try
		{
			ejb = cLfValue.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}