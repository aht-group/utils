package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeElementFactory<
										TTG extends JeeslLfTargetTimeGroup<?,?>,
										TTE extends JeeslLfTargetTimeElement<?,TTG>>
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