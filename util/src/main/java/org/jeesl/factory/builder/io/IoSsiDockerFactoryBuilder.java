package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.data.EjbIoSsiSystemFactory;
import org.jeesl.factory.ejb.system.io.ssi.docker.EjbIoSsiInstanceFactory;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.io.ssi.docker.JeeslIoSsiHost;
import org.jeesl.interfaces.model.system.io.ssi.docker.JeeslIoSsiInstance;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoSsiDockerFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								SYSTEM extends JeeslIoSsiSystem,
								INSTANCE extends JeeslIoSsiInstance<SYSTEM,HOST>,
								HOST extends JeeslIoSsiHost<L,D>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoSsiDockerFactoryBuilder.class);
	
	private final Class<SYSTEM> cSystem; public Class<SYSTEM> getClassSystem(){return cSystem;}
	private final Class<INSTANCE> cInstance; public Class<INSTANCE> getClassInstance(){return cInstance;}
	private final Class<HOST> cHost; public Class<HOST> getClassHost(){return cHost;}
	
	public IoSsiDockerFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<SYSTEM> cSystem,
								final Class<INSTANCE> cInstance,
								final Class<HOST> cHost)
	{
		super(cL,cD);
		this.cSystem=cSystem;
		this.cInstance=cInstance;
		this.cHost=cHost;
	}
	
	public EjbIoSsiSystemFactory<SYSTEM> ejbSystem() {return new EjbIoSsiSystemFactory<>(cSystem);}
	public EjbIoSsiInstanceFactory<SYSTEM,INSTANCE,HOST> ejbInstance() {return new EjbIoSsiInstanceFactory<>(cInstance);}
}