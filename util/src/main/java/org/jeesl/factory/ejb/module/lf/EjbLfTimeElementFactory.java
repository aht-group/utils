package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTimeElementFactory<TG extends JeeslLfTimeGroup<?,?>,
									TE extends JeeslLfTimeElement<?,TG>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTimeElementFactory.class);

	private final Class<TE> cTimeElement;

	public EjbLfTimeElementFactory(final Class<TE> cTimeElement)
	{
		this.cTimeElement =  cTimeElement;
	}

	public TE build()
	{
		TE ejb = null;
		try
		{
			ejb = cTimeElement.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}