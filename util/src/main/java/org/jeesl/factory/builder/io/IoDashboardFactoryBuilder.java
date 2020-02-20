package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dashboard.EjbDashComponentFactory;
import org.jeesl.factory.ejb.system.io.dashboard.EjbDashboardFactory;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashComponent;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashboard;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoDashboardFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									DBR extends JeeslStatus<DBR,L,D>,
									DB extends JeeslIoDashboard<L,D,DBR,DB>,
									DBC extends JeeslIoDashComponent<L,D,DBC>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDashboardFactoryBuilder.class);

	private final Class<DBR> cResolution; public Class<DBR> getClassResolution() {return cResolution;}
	private final Class<DB> cDashboard; public Class<DB> getClassDashboard() {return cDashboard;}
	private final Class<DBC> cDashComponent; public Class<DBC> getClassDashComponent() {return cDashComponent;}

	public IoDashboardFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DBR> cResolution, final Class<DB> cDashboard, final Class<DBC> cDashComponent)
	{
		super(cL,cD);
		this.cResolution=cResolution;
		this.cDashboard=cDashboard;
		this.cDashComponent = cDashComponent;
	}

	public EjbDashboardFactory<L,D,DBR,DB> dashboard(){return new EjbDashboardFactory<L,D,DBR,DB>(cDashboard);}
	public EjbDashComponentFactory<L,D,DBC> dashComponent(){return new EjbDashComponentFactory<L,D,DBC>(cDashComponent);}
}