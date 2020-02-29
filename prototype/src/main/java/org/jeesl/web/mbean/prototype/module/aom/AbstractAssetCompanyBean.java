package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.aom.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomLevel;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.jsf.util.JeeslSelectManyHandler;
import org.jeesl.model.module.aom.AssetCompanyLazyModel;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAssetCompanyBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											REALM extends JeeslMcsRealm<L,D,REALM,?>, RREF extends EjbWithId,
											COMPANY extends JeeslAomCompany<REALM,SCOPE>,
											SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
											ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
											ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
											ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
											ALEVEL extends JeeslAomLevel<L,D,REALM,?>,
											EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER,FRC>,
											ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
											ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
											USER extends JeeslSimpleUser,
											FRC extends JeeslFileContainer<?,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCompanyBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fAsset;
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,ETYPE> bCache;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fbAsset;
	
	private JeeslSelectManyHandler<SCOPE> smh; public JeeslSelectManyHandler<SCOPE> getSmh() {return smh;}
	
	private AssetCompanyLazyModel<REALM,RREF,COMPANY,SCOPE> lazyCompany; public AssetCompanyLazyModel<REALM,RREF,COMPANY,SCOPE> getLazyCompany() {return lazyCompany;}

    protected REALM realm; public REALM getRealm() {return realm;}
	private RREF rref; public RREF getRref() {return rref;}
	private COMPANY company; public COMPANY getCompany() {return company;} public void setCompany(COMPANY company) {this.company = company;}

	public AbstractAssetCompanyBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		smh = new JeeslSelectManyHandler<>();
		lazyCompany = new AssetCompanyLazyModel<>();
	}

	protected void postConstructAssetCompany(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
													JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,ETYPE> bCache,
													JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fAsset,
													REALM realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		this.bCache=bCache;
		smh.updateList(fAsset.allOrderedPosition(fbAsset.getClassScope()));
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		lazyCompany.setScope(bCache,realm,rref);
	}
	
	public void addManufacturer() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAsset.getClassCompany()));}
		smh.clear();
		company = fbAsset.ejbManufacturer().build(realm,rref);
	}
	
	public void saveManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		company.setScopes(smh.toEjb());
		company = fAsset.save(company);
		bCache.update(realm,rref,company);
	}
	
	public void selectManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		company = fAsset.find(fbAsset.getClassCompany(),company);
		smh.init(company.getScopes());
	}
}