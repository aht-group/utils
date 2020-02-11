package org.jeesl.factory.ejb.system.io.domain;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyDomainQueryFactory<L extends JeeslLang, D extends JeeslDescription,
										DOMAIN extends JeeslDomain<L,?>,
										QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
										PATH extends JeeslDomainPath<L,D,QUERY,?,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDomainQueryFactory.class);
	
	private final Class<QUERY> cQuery;
    
	public EjbSurveyDomainQueryFactory(final Class<QUERY> cQuery)
	{       
        this.cQuery = cQuery;
	}
    
	public QUERY build(DOMAIN domain, List<QUERY> list)
	{
		QUERY ejb = null;
		try
		{
			ejb = cQuery.newInstance();
			ejb.setDomain(domain);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public PATH toEffectiveAttribute(QUERY query)
	{
		return query.getPaths().get(query.getPaths().size()-1);
	}
}