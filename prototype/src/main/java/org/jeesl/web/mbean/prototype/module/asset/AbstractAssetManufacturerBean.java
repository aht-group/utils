package org.jeesl.web.mbean.prototype.module.asset;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAssetManufacturerBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											REALM extends JeeslAssetRealm<L,D,REALM,?>, RREF extends EjbWithId,
											ASSET extends JeeslAsset<REALM,ASSET,STATUS,TYPE>,
											MANU extends JeeslAssetManufacturer<REALM>,
											STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
											TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetManufacturerBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset;
	
	private List<MANU> manufacturers; public List<MANU> getManufacturers() {return manufacturers;} public void setManufacturers(List<MANU> manufacturers) {this.manufacturers = manufacturers;}

    private REALM realm;
    private RREF realmReference;
	private MANU manufacturer; public MANU getManufacturer() {return manufacturer;} public void setManufacturer(MANU manufacturer) {this.manufacturer = manufacturer;}

	public AbstractAssetManufacturerBean(AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
	}

	protected <E extends Enum<E>> void postConstructAssetManufacturer(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
													JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset,
													E eRealm, RREF realmReference)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.realmReference=realmReference;
		reloadManufacturers();
	}
	
	private void reloadManufacturers()
	{
		manufacturers = fAsset.all(fbAsset.getClassManufacturer());
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassManufacturer(),manufacturers));
	}
	
	public void addManufacturer() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAsset.getClassManufacturer()));}
		
		manufacturer = fbAsset.ejbManufacturer().build(realm,realmReference);
	}
	
	public void saveManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		manufacturer = fAsset.save(manufacturer);
		reloadManufacturers();
	}
	
	public void selectManufacturer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		manufacturer = fAsset.find(fbAsset.getClassManufacturer(),manufacturer);
	}
}