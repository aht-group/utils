package org.jeesl.factory.builder.module;

import java.util.List;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LfFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
							R extends JeeslTenantRealm<L,D,R,?>,
							TTG extends JeeslLfTargetTimeGroup<L,TTI>,
							TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
							TTE extends JeeslLfTargetTimeElement<L,TTG>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LfFactoryBuilder.class);
	protected final Class<R> cR; public Class<R> getClassR() {return cR;}
	protected final Class<TTG> cTTG; public Class<TTG> getClassTTG() {return cTTG;}
	protected final Class<TTI> cTTI; public Class<TTI> getClassTTI() {return cTTI;}
	protected final Class<TTE> cTTE; public Class<TTE> getClassTTE() {return cTTE;}


	public LfFactoryBuilder(final Class<L> cL,final Class<D> cD, final Class<R> cR, final Class<TTG> cTTG, final Class<TTI> cTTI, final Class<TTE> cTTE)
	{
		super(cL,cD);
		this.cR = cR;
		this.cTTG = cTTG;
		this.cTTI = cTTI;
		this.cTTE = cTTE;
	}


	public TTG buildTargetTimeGroup() {
		TTG ejb = null;
		try
		{
			ejb = cTTG.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}


	public TTE buildElement(TTG timeGroup, List<TTE> elements) {
		TTE ejb = null;
		try
		{
			ejb = cTTE.newInstance();
			ejb.setGroup(timeGroup);
			elements.add(ejb);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}


	public TTE buildElement(TTG timeGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}