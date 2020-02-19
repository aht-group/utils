package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
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
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.jsf.util.JeeslSelectManyHandler;
import org.jeesl.model.module.aom.AssetCompanyLazyModel;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAssetCompanyBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
											COMPANY extends JeeslAomCompany<REALM,SCOPE>,
											SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
											ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
											ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
											ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
											EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER>,
											ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
											ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
											USER extends JeeslSimpleUser>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCompanyBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fAsset;
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fbAsset;
	
	private JeeslSelectManyHandler<SCOPE> smh; public JeeslSelectManyHandler<SCOPE> getSmh() {return smh;}
	
	private AssetCompanyLazyModel<REALM,RREF,COMPANY,SCOPE> lazyCompany; public AssetCompanyLazyModel<REALM,RREF,COMPANY,SCOPE> getLazyCompany() {return lazyCompany;}

    protected REALM realm; public REALM getRealm() {return realm;}
	private RREF rref; public RREF getRref() {return rref;}
	private COMPANY company; public COMPANY getCompany() {return company;} public void setCompany(COMPANY company) {this.company = company;}

	public AbstractAssetCompanyBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		smh = new JeeslSelectManyHandler<>();
		lazyCompany = new AssetCompanyLazyModel<>();
	}

	protected void postConstructAssetCompany(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
													JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache,
													JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fAsset,
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