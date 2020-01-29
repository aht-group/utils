package org.jeesl.web.mbean.prototype.module.om;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.api.facade.module.JeeslOmFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.builder.module.OmFactoryBuilder;
import org.jeesl.factory.ejb.module.om.EjbOmCompanyFactory;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.om.JeeslOmCompany;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractOmCompanyBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									REALM extends JeeslAssetRealm<L,D,REALM,?>, RREF extends EjbWithId,
									COMPANY extends JeeslOmCompany<REALM>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractOmCompanyBean.class);
	
	protected JeeslOmFacade<L,D,COMPANY> fOm;
	
	private final OmFactoryBuilder<L,D,REALM,COMPANY> fbOm;
	private final AssetFactoryBuilder<L,D,REALM,?,?,?,?> fbAsset;
	
	private final EjbOmCompanyFactory<REALM,COMPANY> efCompany;
	
	private List<COMPANY> companies; public List<COMPANY> getCompanies() {return companies;} public void setCompanies(List<COMPANY> companies) {this.companies = companies;}

    private REALM realm;
    private RREF rRef;
	private COMPANY company;  public COMPANY getCompany() {return company;} public void setCompany(COMPANY company) {this.company = company;}

	public AbstractOmCompanyBean(AssetFactoryBuilder<L,D,REALM,?,?,?,?> fbAsset, OmFactoryBuilder<L,D,REALM,COMPANY> fbOm)
	{
		super(fbOm.getClassL(),fbOm.getClassD());
		this.fbAsset=fbAsset;
		this.fbOm=fbOm;
		
		efCompany = fbOm.ejbCompany();
	}

	protected <E extends Enum<E>> void postConstructOmCompany(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										JeeslOmFacade<L,D,COMPANY> fOm,
										E eRealm, RREF rRef)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fOm=fOm;
		
		realm = fOm.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.rRef=rRef;
		
		reloadCompanies();
	}
	
	private void reloadCompanies()
	{
		companies = fOm.all(fbOm.getClassCompany());
		logger.info(AbstractLogMessage.reloaded(fbOm.getClassCompany(),companies));
	}
	
	public void addCompany() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbOm.getClassCompany()));}
		
		company = efCompany.build(realm,rRef);
	}
	
	public void saveCompany() throws JeeslConstraintViolationException, JeeslLockingException
	{
		efCompany.converter(fOm,company);
		company = fOm.save(company);
		reloadCompanies();
	}
	
	public void selectCompany() throws JeeslConstraintViolationException, JeeslLockingException
	{
		company = fOm.find(fbOm.getClassCompany(),company);
	}
}