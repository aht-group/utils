package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.its.EjbItsFactory;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								REALM extends JeeslMcsRealm<L,D,REALM,?>,
								ISSUE extends JeeslItsIssue<REALM,ISSUE>,
								STATUS extends JeeslItsIssueStatus<L,D,REALM,STATUS,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ItsFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<ISSUE> cIssue; public Class<ISSUE> getClassIssue() {return cIssue;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	
	public ItsFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ISSUE> cIssue,
								final Class<STATUS> cStatus)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cIssue=cIssue;
		this.cStatus=cStatus;
	}
	
	public EjbItsFactory<REALM,ISSUE,STATUS> ejbIssue() {return new EjbItsFactory<>(this);}
}