package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.om.EjbOmCompanyFactory;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.om.JeeslOmCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class OmFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAssetRealm<L,D,REALM,?>,
								COMPANY extends JeeslOmCompany<REALM>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(OmFactoryBuilder.class);
	
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}

	public OmFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<COMPANY> cCompany)
	{       
		super(cL,cD);
		this.cCompany=cCompany;

	}
	
	public EjbOmCompanyFactory<REALM,COMPANY> ejbCompany(){return new EjbOmCompanyFactory<>(cCompany);}
}