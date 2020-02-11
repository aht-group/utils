package org.jeesl.factory.ejb.system.io.domain;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDomainSetFactory<L extends JeeslLang, D extends JeeslDescription,	
				DOMAIN extends JeeslDomain<L,?>,
				SET extends JeeslDomainSet<L,D,DOMAIN>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDomainSetFactory.class);
	
	private final Class<SET> cSet;
    
	public EjbDomainSetFactory(final Class<SET> cSet)
	{       
        this.cSet = cSet;
	}
    
	public SET build(DOMAIN domain, List<SET> list)
	{
		SET ejb = null;
		try
		{
			ejb = cSet.newInstance();
			ejb.setDomain(domain);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}