package org.jeesl.factory.ejb.module.ts;

import java.util.List;

import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTsMutliPointFactory<L extends JeeslLang, D extends JeeslDescription,
									CAT extends JeeslStatus<CAT,L,D>,
									SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
									ST extends JeeslStatus<ST,L,D>,
									UNIT extends JeeslStatus<UNIT,L,D>,
									MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
									EC extends JeeslTsEntityClass<L,D,CAT>,
									INT extends JeeslStatus<INT,L,D>
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsMutliPointFactory.class);
	
	private final Class<MP> cMp;
    
	public EjbTsMutliPointFactory(final Class<MP> cMp)
	{       
        this.cMp=cMp;
	}
	
	public MP build(SCOPE scope, List<MP> list)
	{
		MP ejb = null;
		try
		{
			ejb = cMp.newInstance();
			ejb.setScope(scope);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}