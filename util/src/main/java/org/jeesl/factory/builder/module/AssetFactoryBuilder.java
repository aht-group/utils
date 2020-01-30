package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetManufacturerFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetTypeFactory;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AssetFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAssetRealm<L,D,REALM,?>,
								ASSET extends JeeslAsset<REALM,ASSET,MANU,STATUS,TYPE>,
								MANU extends JeeslAssetManufacturer<REALM>,
								STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
								TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AssetFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<ASSET> cAsset; public Class<ASSET> getClassAsset() {return cAsset;}
	private final Class<MANU> cManu; public Class<MANU> getClassManufacturer() {return cManu;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}

	public AssetFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ASSET> cAsset,
								final Class<MANU> cManu,
								final Class<STATUS> cStatus,
								final Class<TYPE> cType)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cAsset=cAsset;
		this.cManu=cManu;
		this.cStatus=cStatus;
		this.cType=cType;
	}
	
	public EjbAssetManufacturerFactory<REALM,MANU> ejbManufacturer() {return new EjbAssetManufacturerFactory<>(cManu);}
	public EjbAssetTypeFactory<REALM,TYPE> ejbType() {return new EjbAssetTypeFactory<>(cType);}
	public EjbAssetFactory<REALM,ASSET,MANU,STATUS,TYPE> ejbAsset() {return new EjbAssetFactory<>(cAsset);}
}