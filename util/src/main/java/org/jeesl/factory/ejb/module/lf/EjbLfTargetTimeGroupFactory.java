package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeGroupFactory<L extends JeeslLang, D extends JeeslDescription,
										R extends JeeslTenantRealm<L,D,R,?>,
										LF extends JeeslLfLogframe,
										TTG extends JeeslLfTargetTimeGroup<L,TTI>,
										TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>>
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