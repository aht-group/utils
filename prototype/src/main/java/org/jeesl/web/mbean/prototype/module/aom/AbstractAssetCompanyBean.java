package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;
import java.util.List;

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
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.jsf.util.JeeslSelectManyHandler;
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
											EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS>,
											ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
											ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCompanyBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset;
	
	private JeeslSelectManyHandler<SCOPE> smh; public JeeslSelectManyHandler<SCOPE> getSmh() {return smh;}
	
	private List<COMPANY> companies; public List<COMPANY> getCompanies() {return companies;}

    private REALM realm;
    private RREF realmReference;
	private COMPANY company; public COMPANY getCompany() {return company;} public void setCompany(COMPANY company) {this.company = company;}

	public AbstractAssetCompanyBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		smh = new JeeslSelectManyHandler<>();
	}

	protected <E extends Enum<E>> void postConstructAssetManufacturer(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
//													JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,TYPE> bCache,
													JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset,
													E eRealm, RREF realmReference)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		smh.updateList(fAsset.allOrderedPosition(fbAsset.getClassScope()));
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.realmReference=realmReference;
		reloadManufacturers();
	}
	
	private void reloadManufacturers()
	{
		companies = fAsset.all(fbAsset.getClassCompany());
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassCompany(),companies));
	}
	
	public void addManufacturer() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAsset.getClassCompany()));}
		smh.clear();
		company = fbAsset.ejbManufacturer().build(realm,realmReference);
	}
	
	public void saveManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		company.setScopes(smh.toEjb());
		company = fAsset.save(company);
		reloadManufacturers();
	}
	
	public void selectManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		company = fAsset.find(fbAsset.getClassCompany(),company);
		smh.init(company.getScopes());
	}
}