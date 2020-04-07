package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.its.EjbItsFactory;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfig;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfigOption;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								R extends JeeslMcsRealm<L,D,R,?>,
								C extends JeeslItsConfig<L,D,R,O>,
								O extends JeeslItsConfigOption<L,D,O,?>,
								I extends JeeslItsIssue<R,I>,
								STATUS extends JeeslItsIssueStatus<L,D,R,STATUS,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ItsFactoryBuilder.class);
	
	private final Class<R> cRealm; public Class<R> getClassRealm() {return cRealm;}
	
	private final Class<C> cConfig; public Class<C> getClassConfig() {return cConfig;}
	private final Class<O> cOption; public Class<O> getClassOption() {return cOption;}
	
	private final Class<I> cIssue; public Class<I> getClassIssue() {return cIssue;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	
	public ItsFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<R> cRealm,
								final Class<C> cConfig,
								final Class<O> cOption,
								final Class<I> cIssue,
								final Class<STATUS> cStatus)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cIssue=cIssue;
		this.cConfig=cConfig;
		this.cOption=cOption;
		this.cStatus=cStatus;
	}
	
	public EjbItsFactory<R,I,STATUS> ejbIssue() {return new EjbItsFactory<>(this);}
}