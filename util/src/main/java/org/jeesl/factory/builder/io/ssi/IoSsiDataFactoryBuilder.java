package org.jeesl.factory.builder.io.ssi;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.core.EjbIoSsiCredentialFactory;
import org.jeesl.factory.ejb.system.io.ssi.core.EjbIoSsiSystemFactory;
import org.jeesl.factory.ejb.system.io.ssi.data.EjbIoSsiAttributeFactory;
import org.jeesl.factory.ejb.system.io.ssi.data.EjbIoSsiDataFactory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.core.JeeslIoSsiCredential;
import org.jeesl.interfaces.model.system.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiCleaning;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiLink;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoSsiDataFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								SYSTEM extends JeeslIoSsiSystem<L,D>,
								CRED extends JeeslIoSsiCredential<SYSTEM>,
								MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
								ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
								DATA extends JeeslIoSsiData<MAPPING,LINK>,
								LINK extends JeeslIoSsiLink<L,D,LINK,?>,
								ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>,
								CLEANING extends JeeslIoSsiCleaning<L,D,CLEANING,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoSsiDataFactoryBuilder.class);
	
	private final Class<SYSTEM> cSystem; public Class<SYSTEM> getClassSystem(){return cSystem;}
	private final Class<CRED> cCredential; public Class<CRED> getClassCredential(){return cCredential;}
	private final Class<MAPPING> cMapping; public Class<MAPPING> getClassMapping(){return cMapping;}
	private final Class<ATTRIBUTE> cAttribute; public Class<ATTRIBUTE> getClassAttribute(){return cAttribute;}
	private final Class<DATA> cData; public Class<DATA> getClassData(){return cData;}
	private final Class<LINK> cLink; public Class<LINK> getClassLink(){return cLink;}
	private final Class<ENTITY> cEntity; public Class<ENTITY> getClassEntity(){return cEntity;}
	private final Class<CLEANING> cCleaning; public Class<CLEANING> getClassCleaning(){return cCleaning;}
	
	public IoSsiDataFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<SYSTEM> cSystem, final Class<CRED> cCredential,
								final Class<MAPPING> cMapping, final Class<ATTRIBUTE> cAttribute,
								final Class<DATA> cData, final Class<LINK> cLink, final Class<ENTITY> cEntity,
								final Class<CLEANING> cCleaning)
	{
		super(cL,cD);
		this.cSystem=cSystem;
		this.cCredential=cCredential;
		this.cMapping=cMapping;
		this.cAttribute=cAttribute;
		this.cData=cData;
		this.cLink=cLink;
		this.cEntity=cEntity;
		this.cCleaning=cCleaning;
	}
	
	public EjbIoSsiSystemFactory<SYSTEM> ejbSystem() {return new EjbIoSsiSystemFactory<>(cSystem);}
	public EjbIoSsiCredentialFactory<SYSTEM,CRED> ejbCredential() {return new EjbIoSsiCredentialFactory<>(cCredential);}
	public EjbIoSsiAttributeFactory<MAPPING,ATTRIBUTE,ENTITY> ejbAttribute() {return new EjbIoSsiAttributeFactory<>(cAttribute);}
	public EjbIoSsiDataFactory<MAPPING,DATA,LINK> ejbData() {return new EjbIoSsiDataFactory<>(cData);}
}