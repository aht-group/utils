package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfConfigurationFactory<
										LFC extends JeeslLfConfiguration<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfConfigurationFactory.class);

	private final Class<LFC> cLfConfiguration;

	public EjbLfConfigurationFactory(final Class<LFC> cLfConfiguration)
	{
		this.cLfConfiguration =  cLfConfiguration;
	}

	public LFC build()
	{
		LFC ejb = null;
		try
		{
			ejb = cLfConfiguration.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}