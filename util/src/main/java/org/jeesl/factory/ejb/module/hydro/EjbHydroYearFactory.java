package org.jeesl.factory.ejb.module.hydro;

import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHydroYearFactory<L extends JeeslLang, D extends JeeslDescription,
								HD extends JeeslStatus<HD,L,D>,
								HY extends JeeslHydroYear<L,D,HD,HY>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHydroYearFactory.class);

	final Class<HY> cYear;

	public EjbHydroYearFactory(final Class<HY> cYear)
	{
        this.cYear = cYear;
	}

	public static <L extends JeeslLang, D extends JeeslDescription,
					HD extends JeeslStatus<HD,L,D>,
					HY extends JeeslHydroYear<L,D,HD,HY>>
	EjbHydroYearFactory<L,D,HD,HY> factory(final Class<HY> cYear)
	{
		return new EjbHydroYearFactory<L,D,HD,HY>(cYear);
	}

	public HY build(String code)
	{
		HY ejb = null;
		try
		{
			ejb = cYear.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}

}