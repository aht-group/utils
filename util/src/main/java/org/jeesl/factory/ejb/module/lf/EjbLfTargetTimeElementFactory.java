package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeElementFactory<
										TTG extends JeeslLfTimeGroup<?,?>,
										TTE extends JeeslLfTimeElement<?,TTG>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTargetTimeElementFactory.class);

	private final Class<TTE> cTargetTimeElement;

	public EjbLfTargetTimeElementFactory(final Class<TTE> cTargetTimeElement)
	{
		this.cTargetTimeElement =  cTargetTimeElement;
	}

	public TTE build()
	{
		TTE ejb = null;
		try
		{
			ejb = cTargetTimeElement.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}