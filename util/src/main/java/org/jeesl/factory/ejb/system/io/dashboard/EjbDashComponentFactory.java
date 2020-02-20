package org.jeesl.factory.ejb.system.io.dashboard;

import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashComponent;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDashComponentFactory<L extends JeeslLang, D extends JeeslDescription,
								DBC extends JeeslIoDashComponent<L,D,DBC>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDashComponentFactory.class);

	final Class<DBC> cDashComponent;

	public EjbDashComponentFactory(final Class<DBC> cDashComponent)
	{
        this.cDashComponent = cDashComponent;
	}

	public static <L extends JeeslLang, D extends JeeslDescription,
				DBC extends JeeslIoDashComponent<L,D,DBC>>
				EjbDashComponentFactory<L,D,DBC> factory(final Class<DBC> cDashComponent)
	{
		return new EjbDashComponentFactory<L,D,DBC>(cDashComponent);
	}

	public DBC build(String code)
	{
		DBC ejb = null;
		try
		{
			ejb = cDashComponent.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}

}