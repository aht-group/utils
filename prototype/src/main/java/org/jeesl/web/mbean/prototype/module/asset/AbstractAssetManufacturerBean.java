package org.jeesl.web.mbean.prototype.module.asset;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.POST;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.api.facade.module.JeeslBbFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.bb.JeeslBbPublishingMode;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAssetManufacturerBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											ASSET extends JeeslAsset,
											MANU extends JeeslAssetManufacturer,
											AS extends JeeslAssetStatus<L,D,AS,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetManufacturerBean.class);
	
	protected JeeslAssetFacade<L,D,ASSET,MANU,AS> fAsset;
	
	private final AssetFactoryBuilder<L,D,ASSET,MANU,AS> fbAsset;
	
//	protected final SbSingleHandler<SCOPE> sbhScope; public SbSingleHandler<SCOPE> getSbhScope() {return sbhScope;}
	
	private List<MANU> manufacturers; public List<MANU> getManufacturers() {return manufacturers;} public void setManufacturers(List<MANU> manufacturers) {this.manufacturers = manufacturers;}

	private MANU manufacturer; public MANU getManufacturer() {return manufacturer;} public void setManufacturer(MANU manufacturer) {this.manufacturer = manufacturer;}

	public AbstractAssetManufacturerBean(AssetFactoryBuilder<L,D,ASSET,MANU,AS> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
//		sbhScope = new SbSingleHandler<SCOPE>(fbBb.getClassScope(),this);
	}

	protected void postConstructAssetManufacturer(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslAssetFacade<L,D,ASSET,MANU,AS> fAsset)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		reloadManufacturers();
	}
	
//	@Override public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
//	{
//		reloadBoards();
//	}
	
	private void reloadManufacturers()
	{
		manufacturers = fAsset.all(fbAsset.getClassManufacturer());
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassManufacturer(),manufacturers));
	}
	
	public void addManufacturer() throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAsset.getClassManufacturer()));}
		
		manufacturer = fbAsset.ejbManufacturer().build();
	}
	
	public void saveManufacturer() throws UtilsConstraintViolationException, UtilsLockingException
	{
//		board.setPublishing(fBb.find(fbBb.getClassPublishing(),board.getPublishing()));
		manufacturer = fAsset.save(manufacturer);
		reloadManufacturers();
	}
}