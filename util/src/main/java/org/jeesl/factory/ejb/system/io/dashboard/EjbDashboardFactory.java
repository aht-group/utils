package org.jeesl.factory.ejb.system.io.dashboard;

import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashboard;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDashboardFactory<L extends JeeslLang, D extends JeeslDescription,
								DBR extends JeeslStatus<DBR,L,D>,
								DB extends JeeslIoDashboard<L,D,DBR,DB>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDashboardFactory.class);

	final Class<DB> cDashboard;

	public EjbDashboardFactory(final Class<DB> cDashboard)
	{
        this.cDashboard = cDashboard;
	}

	public static <L extends JeeslLang, D extends JeeslDescription,
				DBR extends JeeslStatus<DBR,L,D>,
				DB extends JeeslIoDashboard<L,D,DBR,DB>>
				EjbDashboardFactory<L,D,DBR,DB> factory(final Class<DB> cDashboard)
	{
		return new EjbDashboardFactory<L,D,DBR,DB>(cDashboard);
	}

	public DB build(String code)
	{
		DB ejb = null;
		try
		{
			ejb = cDashboard.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}

}