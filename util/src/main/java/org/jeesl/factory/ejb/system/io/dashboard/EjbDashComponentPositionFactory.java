package org.jeesl.factory.ejb.system.io.dashboard;

import org.jeesl.interfaces.model.io.dash.JeeslIoDashComponent;
import org.jeesl.interfaces.model.io.dash.JeeslIoDashComponentPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDashComponentPositionFactory<DBC extends JeeslIoDashComponent<?,?,DBC>, DBCP extends JeeslIoDashComponentPosition<?,?,?,DBC,?,DBCP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDashComponentPositionFactory.class);

	final Class<DBCP> cDashComponentPosition;

	public EjbDashComponentPositionFactory(final Class<DBCP> cDashComponentPosition)
	{
        this.cDashComponentPosition = cDashComponentPosition;
	}

	public static <DBC extends JeeslIoDashComponent<?,?,DBC>,DBCP extends JeeslIoDashComponentPosition<?,?,?,DBC,?,DBCP>>
				EjbDashComponentPositionFactory<DBC,DBCP> factory(final Class<DBCP> cDashComponentPosition)
	{
		return new EjbDashComponentPositionFactory<DBC,DBCP>(cDashComponentPosition);
	}

	public DBCP build()
	{
		DBCP ejb = null;
		try
		{
			ejb = cDashComponentPosition.newInstance();
			ejb.setRow(0);
			ejb.setWidth(0);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}