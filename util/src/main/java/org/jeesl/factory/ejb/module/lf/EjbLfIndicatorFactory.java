package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfUnit;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfVerificationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfIndicatorFactory<LFI extends JeeslLfIndicator<?,?,IT,IU,IV,?,?>,
									IT extends JeeslLfIndicatorType<?,?,?,IT,?>,
									IU extends JeeslLfUnit<?,?,?,IU,?>,
									IV extends JeeslLfVerificationSource<?,?,?,IV,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfIndicatorFactory.class);

	private final Class<LFI> cLfIndicator;

	public EjbLfIndicatorFactory(final Class<LFI> cLfIndicator)
	{
		this.cLfIndicator =  cLfIndicator;
	}

	public LFI build()
	{
		LFI ejb = null;
		try
		{
			ejb = cLfIndicator.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}