package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTimeGroupFactory<TG extends JeeslLfTimeGroup<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTimeGroupFactory.class);

	private final Class<TG> cTimeGroup;

	public EjbLfTimeGroupFactory(final Class<TG> cTimeGroup)
	{
        this.cTimeGroup = cTimeGroup;
	}

	public TG build()
	{
		TG ejb = null;
		try
		{
			ejb = cTimeGroup.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}