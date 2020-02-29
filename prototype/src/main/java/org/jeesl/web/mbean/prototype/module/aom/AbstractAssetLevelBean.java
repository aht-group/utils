package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetLevelFactory;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomLevel;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.PositionListReorderer;

public abstract class AbstractAssetLevelBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										S extends EjbWithId, G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslStatus<GT,L,D>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>,
										REALM extends JeeslMcsRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
										ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,G>,
										ALEVEL extends JeeslAomLevel<L,D,REALM,G>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER,FRC>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
										USER extends JeeslSimpleUser,
										FRC extends JeeslFileContainer<?,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetLevelBean.class);
	
	private JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fAsset;
	private JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic;
	
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,ETYPE> bCache;
	
	private final SvgFactoryBuilder<L,D,G,GT,F,FS> fbSvg;
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fbAsset;
	
    private REALM realm;
    private RREF rref;
    
    private final List<ALEVEL> levels; public List<ALEVEL> getLevels() {return levels;}
 
    private ALEVEL level; public ALEVEL getLevel() {return level;} public void setLevel(ALEVEL level) {this.level = level;}

	public AbstractAssetLevelBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fbAsset, SvgFactoryBuilder<L,D,G,GT,F,FS> fbSvg)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		this.fbSvg=fbSvg;

		levels = new ArrayList<>();
	}
	
	protected void postConstructAomLevel(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,ETYPE> bCache,
									JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ALEVEL,EVENT,ETYPE,ESTATUS,USER,FRC> fAsset,
									JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic,
									REALM realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.bCache=bCache;
		this.fAsset=fAsset;
		this.fGraphic=fGraphic;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		reload();
	}
	
	private void reload()
	{
		levels.clear();
		levels.addAll(fAsset.fAomLevels(realm,rref));
	}
	
	private void reset(boolean rLevel)
	{
		if(rLevel) {level=null;}
	}
	
	public void selectLevel()
	{
		
	}
	
	public void addLevel()
	{
		level = fbAsset.ejbLevel().build(realm,rref,levels);
		level.setName(efLang.createEmpty(bTranslation.getLocales()));
		level.setDescription(efDescription.createEmpty(bTranslation.getLocales()));
	}
	
	public void saveLevel() throws JeeslConstraintViolationException, JeeslLockingException
	{
		level = fAsset.save(level);
		reload();
//		bCache.update(realm, rref, type);
	}
	
	public void deleteLevel() throws JeeslLockingException
	{
		try
		{
			fAsset.rm(level);
//			bCache.delete(realm,rref,level);
			reload();
			reset(true);
		}
		catch (JeeslConstraintViolationException e) {bMessage.errorConstraintViolationInUse();}
	}
    
	public void handleFileUpload(FileUploadEvent event) throws JeeslConstraintViolationException, JeeslLockingException
	{
		UploadedFile file = event.getFile();
		logger.info("Received file with a size of " +file.getSize());
		if(level.getGraphic()==null)
		{
			GT gt = fAsset.fByEnum(fbSvg.getClassGraphicType(), JeeslGraphicType.Code.svg);
			G g = fbSvg.efGraphic().build(gt);
			g = fAsset.save(g);
			level.setGraphic(g);
			level = fAsset.save(level);
			level.getGraphic().setData(file.getContents());
			level = fAsset.save(level);
		}
		else
		{
//			try
//			{
//				G g = fGraphic.fGraphic(fbAsset.getClassAssetLevel(),level);
//				g.setData(file.getContents());
//				fAsset.save(g);
//			}
//			catch (JeeslNotFoundException e) {e.printStackTrace();}
		}
	}
	
	public void reorder() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fAsset,levels);}
}