package org.jeesl.factory.builder.module;

import java.util.Comparator;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetCompanyFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetEventFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetTypeFactory;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.util.comparator.ejb.module.asset.EjbAssetComparator;
import org.jeesl.util.comparator.ejb.module.asset.EjbEventComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								REALM extends JeeslAomRealm<L,D,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,SCOPE>,
								SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
								ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
								ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
								ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER>,
								ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
								USER extends JeeslSimpleUser>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AssetFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<ASSET> cAsset; public Class<ASSET> getClassAsset() {return cAsset;}
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}
	private final Class<ASTATUS> cStatus; public Class<ASTATUS> getClassStatus() {return cStatus;}
	private final Class<ATYPE> cAssetType; public Class<ATYPE> getClassAssetType() {return cAssetType;}
	private final Class<EVENT> cEvent; public Class<EVENT> getClassEvent() {return cEvent;}
	private final Class<ETYPE> cEventType; public Class<ETYPE> getClassEventType() {return cEventType;}
	private final Class<ESTATUS> cEventStatus; public Class<ESTATUS> getClassEventStatus() {return cEventStatus;}
	
	public AssetFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ASSET> cAsset,
								final Class<COMPANY> cCompany,
								final Class<SCOPE> cScope,
								final Class<ASTATUS> cStatus,
								final Class<ATYPE> cAssetType,
								final Class<EVENT> cEvent,
								final Class<ETYPE> cEventType,
								final Class<ESTATUS> cEventStatus)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cCompany=cCompany;
		this.cScope=cScope;
		this.cAsset=cAsset;
		this.cStatus=cStatus;
		this.cAssetType=cAssetType;
		this.cEvent=cEvent;
		this.cEventType=cEventType;
		this.cEventStatus=cEventStatus;
	}
	
	public EjbAssetCompanyFactory<REALM,COMPANY,SCOPE> ejbManufacturer() {return new EjbAssetCompanyFactory<>(cCompany);}
	public EjbAssetTypeFactory<REALM,ATYPE> ejbType() {return new EjbAssetTypeFactory<>(cAssetType);}
	public EjbAssetFactory<REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE> ejbAsset() {return new EjbAssetFactory<>(this);}
	public EjbAssetEventFactory<COMPANY,ASSET,EVENT,ETYPE,ESTATUS,USER> ejbEvent() {return new EjbAssetEventFactory<>(this);}
	
	public Comparator<ASSET> cpAsset(EjbAssetComparator.Type type){return new EjbAssetComparator<ASSET>().factory(type);}
	public Comparator<EVENT> cpEvent(EjbEventComparator.Type type){return new EjbEventComparator<EVENT>().factory(type);}
}