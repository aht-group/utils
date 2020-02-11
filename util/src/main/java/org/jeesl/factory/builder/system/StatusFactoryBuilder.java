package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusFactoryBuilder<L extends JeeslLang,
									D extends JeeslDescription,
									LOC extends JeeslStatus<LOC,L,D>>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(StatusFactoryBuilder.class);
	
	private final Class<LOC> cLoc; public Class<LOC> getClassLocale(){return cLoc;}
	private final Class<?> cStatus; public Class<?> getClassStatus(){return cStatus;}
	
	public StatusFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<?> cStatus)
	{       
		super(cL,cD);
		this.cLoc = cLoc;
		this.cStatus=cStatus;
	}
	
	public EjbLangFactory<L> ejbLang(){return new EjbLangFactory<L>(cL);}
	public EjbDescriptionFactory<D> ejbDescription(){return new EjbDescriptionFactory<D>(cD);}
	
	public <S extends JeeslStatus<S,L,D>> EjbStatusFactory<S,L,D> ejbStatus(final Class<S> cS) {return new EjbStatusFactory<S,L,D>(cS,cL,cD);}
}