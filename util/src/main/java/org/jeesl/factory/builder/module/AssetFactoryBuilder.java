package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetCompanyFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetTypeFactory;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AssetFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAomRealm<L,D,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,SCOPE>,
								SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
								ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
								STATUS extends JeeslAomStatus<L,D,STATUS,?>,
								TYPE extends JeeslAomType<L,D,REALM,TYPE,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AssetFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<ASSET> cAsset; public Class<ASSET> getClassAsset() {return cAsset;}
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}

	public AssetFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ASSET> cAsset,
								final Class<COMPANY> cCompany,
								final Class<SCOPE> cScope,
								final Class<STATUS> cStatus,
								final Class<TYPE> cType)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cCompany=cCompany;
		this.cScope=cScope;
		this.cAsset=cAsset;
		this.cStatus=cStatus;
		this.cType=cType;
	}
	
	public EjbAssetCompanyFactory<REALM,COMPANY,SCOPE> ejbManufacturer() {return new EjbAssetCompanyFactory<>(cCompany);}
	public EjbAssetTypeFactory<REALM,TYPE> ejbType() {return new EjbAssetTypeFactory<>(cType);}
	public EjbAssetFactory<REALM,ASSET,COMPANY,SCOPE,STATUS,TYPE> ejbAsset() {return new EjbAssetFactory<>(cAsset);}
}