package org.jeesl.factory.ejb.module.lf;

import java.util.List;

import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfTargetTimeElementFactory<L extends JeeslLang, D extends JeeslDescription,
										R extends JeeslTenantRealm<L,D,R,?>,
										TTG extends JeeslLfTargetTimeGroup<L,TTI>,
										TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
										TTE extends JeeslLfTargetTimeElement<L,TTG>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfTargetTimeElementFactory.class);

	private final Class<TTE> cTargetTimeElement;

	public EjbLfTargetTimeElementFactory(final Class<TTE> cTargetTimeElement)
	{
		this.cTargetTimeElement =  cTargetTimeElement;
	}

	public TTE build(TTG timeGroup, List<TTE> elements)
	{
		TTE ejb = null;
		try
		{
			ejb = cTargetTimeElement.newInstance();
			ejb.setGroup(timeGroup);
			elements.add(ejb);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}