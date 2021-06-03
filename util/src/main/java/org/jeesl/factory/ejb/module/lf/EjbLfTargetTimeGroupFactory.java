package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeGroupFactory<TTG extends JeeslLfTimeGroup<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTargetTimeGroupFactory.class);

	private final Class<TTG> cTargetTimeGroup;

	public EjbLfTargetTimeGroupFactory(final Class<TTG> cTargetTimeGroup)
	{
        this.cTargetTimeGroup = cTargetTimeGroup;
	}

	public TTG build()
	{
		TTG ejb = null;
		try
		{
			ejb = cTargetTimeGroup.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}