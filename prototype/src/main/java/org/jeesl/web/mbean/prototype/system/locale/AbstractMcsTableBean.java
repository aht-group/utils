package org.jeesl.web.mbean.prototype.system.locale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.system.StatusFactoryBuilder;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFigureFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusWithSymbol;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.parent.EjbWithParent;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithGraphic;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithGraphicFigure;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithImage;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithImageAlt;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.exlp.util.io.StringUtil;

public class AbstractMcsTableBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
										REALM extends JeeslMcsRealm<L,D,REALM,G>,
										G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslStatus<GT,L,D>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>,
										RE extends JeeslRevisionEntity<L,D,?,?,?,?>
>
			extends AbstractAdminBean<L,D>
			implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMcsTableBean.class);
	private static final long serialVersionUID = 1L;

	protected JeeslFacade fUtils;
	
	private final StatusFactoryBuilder<L,D,LOC> fbStatus;
	private final SvgFactoryBuilder<L,D,G,GT,F,FS> fbSvg;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,?,?,?,?> fbRevision;
	
	protected boolean allowSvg; public boolean isAllowSvg() {return allowSvg;}
	private boolean showDescription; public boolean isShowDescription() {return showDescription;}
	
	protected boolean supportsSymbol; public boolean getSupportsSymbol(){return supportsSymbol;}
	protected boolean supportsGraphic; public boolean getSupportsGraphic() {return supportsGraphic;}
	protected boolean supportsFigure; public boolean isSupportsFigure() {return supportsFigure;}

	protected long index;
	protected Map<Long,Boolean> allowAdditionalElements; public Map<Long, Boolean> getAllowAdditionalElements(){return allowAdditionalElements;}
	
	protected Object category; public Object getCategory() {return category;} public void setCategory(Object category) {this.category = category;}
	protected Object status; public Object getStatus() {return status;} public void setStatus(Object status) {this.status = status;}
	private G graphic; public G getGraphic() {return graphic;} public void setGraphic(G graphic) {this.graphic = graphic;}
	private RE entity; public RE getEntity() {return entity;}
	
	@SuppressWarnings("rawtypes")
	protected Class cl;
	
	private final Map<EjbWithPosition,RE> mapEntity; public Map<EjbWithPosition, RE> getMapEntity() {return mapEntity;}
	protected final List<EjbWithPosition> categories; public List<EjbWithPosition> getCategories(){return categories;}
	protected List<EjbWithPosition> parents; public List<EjbWithPosition> getParents(){return parents;}
	protected List<EjbWithPosition> items; public List<EjbWithPosition> getItems() {return items;}
	private List<GT> graphicTypes; public List<GT> getGraphicTypes() {return graphicTypes;}
	private List<FS> graphicStyles; public List<FS> getGraphicStyles() {return graphicStyles;}
	private List<F> figures; public List<F> getFigures() {return figures;}
	
	private F figure; public F getFigure() {return figure;} public void setFigure(F figure) {this.figure = figure;}

	protected long parentId; public long getParentId(){return parentId;}public void setParentId(long parentId){this.parentId = parentId;}
	
	protected final EjbGraphicFactory<L,D,G,GT,F,FS> efGraphic;
	private final EjbGraphicFigureFactory<L,D,G,GT,F,FS> efFigure;
	
	public AbstractMcsTableBean(StatusFactoryBuilder<L,D,LOC> fbStatus,
									SvgFactoryBuilder<L,D,G,GT,F,FS> fbSvg,
									IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,?,?,?,?> fbRevision)
	{
		super(fbStatus.getClassL(),fbStatus.getClassD());
		this.fbStatus=fbStatus;
		this.fbSvg=fbSvg;
		this.fbRevision=fbRevision;

		efGraphic = fbSvg.efGraphic();	
		efFigure = fbSvg.efFigure();
		
		index=1;
		
		showDescription = false;
		hasDeveloperAction = false;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		status = null;
		allowAdditionalElements = new Hashtable<Long,Boolean>();
		
		mapEntity = new HashMap<>();
		categories = new ArrayList<EjbWithPosition>();
	}
	
	protected void postConstructOptionTable(JeeslTranslationBean<L,D,LOC> bTranslation,
											JeeslGraphicFacade<L,D,?,G,GT,F,FS> fGraphic,
											JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fUtils=fGraphic;
			
		graphicTypes = fUtils.allOrderedPositionVisible(fbSvg.getClassGraphicType());
		graphicStyles = fUtils.allOrderedPositionVisible(fbSvg.getClassFigureStyle());
	}
	
	private void reset(boolean rEntity)
	{
		if(rEntity) {entity=null;}
	}
	

	
	protected void updateUiForCategory()
	{
		supportsGraphic = EjbWithGraphic.class.isAssignableFrom(cl);
		supportsSymbol = JeeslStatusWithSymbol.class.isAssignableFrom(cl);
		supportsFigure = EjbWithGraphicFigure.class.isAssignableFrom(cl);
	}
	
	protected void debugUi(boolean debug)
	{
		super.debugUi(debug);
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Option Tables Settings");
			logger.info("\tGraphic? "+supportsGraphic);
			logger.info("\tSymbol? "+supportsSymbol);
			logger.info("\tFigure? "+supportsFigure);
		}
	}
	
	protected void loadEntities()
	{
		for(EjbWithPosition p : categories)
		{
			try
			{
				String fqcn = ((EjbWithImage)p).getImage();
				RE e = fUtils.fByCode(fbRevision.getClassEntity(),fqcn);
				mapEntity.put(p,e);
			}
			catch (JeeslNotFoundException e) {}
		}
	}
	
	public void toggleDescription()
	{
		showDescription = !showDescription;
	}
	
	public void selectCategory() throws ClassNotFoundException{selectCategory(true);}
	@SuppressWarnings("unchecked")
	public void selectCategory(boolean reset) throws ClassNotFoundException
	{
		reset(true);
		if(category==null) {logger.error("selectCategory, but category is NULL");}
		
		StringBuilder sb = new StringBuilder();
		sb.append("selectCategory");
		sb.append(" ").append(((EjbWithCode)category).getCode());
		sb.append(" (").append(((EjbWithImageAlt)category).getImageAlt()).append(")");
		sb.append(" allowAdditionalElements:").append(allowAdditionalElements.get(((EjbWithId)category).getId()));
		logger.info(sb.toString());
		
		cl = Class.forName(((EjbWithImage)category).getImage());
		updateUiForCategory();
		
		try {entity = fUtils.fByCode(fbRevision.getClassEntity(), cl.getName());}
		catch (JeeslNotFoundException e) {}
		
		uiAllowAdd = allowAdditionalElements.get(((EjbWithId)category).getId()) || hasDeveloperAction;
		
		reloadStatusEntries();
		if(reset){reset(true,true);}
		debugUi(true);
	}
	
	@SuppressWarnings("unchecked")
	protected void reloadStatusEntries()
	{
		items = fUtils.allOrderedPosition(cl);
	}
	
	@SuppressWarnings("unchecked")
	public void add() throws JeeslConstraintViolationException, InstantiationException, IllegalAccessException, JeeslNotFoundException
	{
		logger.debug("add");
		uiAllowCode=true;
		
		status = cl.newInstance();
		((EjbWithId)status).setId(0);
		((EjbWithCode)status).setCode("enter code");
		((EjbWithLang<L>)status).setName(efLang.createEmpty(localeCodes));
		((EjbWithDescription<D>)status).setDescription(efDescription.createEmpty(localeCodes));
		
		if(supportsGraphic)
		{
			GT type = fUtils.fByCode(fbSvg.getClassGraphicType(), JeeslGraphicType.Code.symbol.toString());
			FS style = fUtils.fByCode(fbSvg.getClassFigureStyle(), JeeslGraphicStyle.Code.circle.toString());
			graphic = efGraphic.buildSymbol(type, style);
			((EjbWithGraphic<G>)status).setGraphic(graphic);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void selectStatus() throws JeeslConstraintViolationException, JeeslNotFoundException, JeeslLockingException
	{
		figures = null; figure=null;
		status = fUtils.find(cl,(EjbWithId)status);
		status = fUtils.loadGraphic(cl,(EjbWithId)status);
		logger.debug("selectStatus");
		status = efLang.persistMissingLangs(fUtils,localeCodes,(EjbWithLang)status);
		status = efDescription.persistMissingLangs(fUtils,localeCodes,(EjbWithDescription)status);
		
		if(((EjbWithParent)status).getParent()!=null)
		{
			parentId=((EjbWithParent)status).getParent().getId();
		}
		
		if(supportsGraphic)
		{
			if(((EjbWithGraphic<G>)status).getGraphic()==null)
			{
				logger.info("Need to create a graphic entity for this status");
				GT type = fUtils.fByCode(fbSvg.getClassGraphicType(), JeeslGraphicType.Code.symbol);
				FS style = fUtils.fByCode(fbSvg.getClassFigureStyle(), JeeslGraphicStyle.Code.circle);
				graphic = fUtils.persist(efGraphic.buildSymbol(type, style));
				((EjbWithGraphic<G>)status).setGraphic(graphic);
				status = fUtils.update(status);
			}
			graphic = ((EjbWithGraphic<G>)status).getGraphic();
			
			if(supportsFigure){reloadFigures();}
		}
		
		uiAllowCode = hasDeveloperAction || hasAdministratorAction;
		if(hasDeveloperAction){uiAllowCode=true;}
		else if(status instanceof JeeslStatusFixedCode)
		{
			for(String fixed : ((JeeslStatusFixedCode)status).getFixedCodes())
			{
				if(fixed.equals(((JeeslStatus)status).getCode()))
				{
					uiAllowCode=false;
				}
			}
		}
				
		debugUi(false);
	}

	@SuppressWarnings("unchecked")
	public void save() throws ClassNotFoundException, JeeslNotFoundException
    {
		boolean debugSave=true;
		try
		{
        	if(supportsGraphic && graphic!=null)
            {
        		graphic.setType(fUtils.find(fbSvg.getClassGraphicType(), ((EjbWithGraphic<G>)status).getGraphic().getType()));
            	if(graphic.getStyle()!=null){graphic.setStyle(fUtils.find(fbSvg.getClassFigureStyle(), ((EjbWithGraphic<G>)status).getGraphic().getStyle()));}
        		
            	((EjbWithGraphic<G>)status).setGraphic(graphic);
//            	if(debugSave){logger.info("Saved "+graphic.getClass().getSimpleName()+" "+graphic.toString());}
            }

        	if(debugSave){logger.info("Saving "+status.getClass().getSimpleName()+" "+status.toString());}
			status = fUtils.save((EjbSaveable)status);
			status = fUtils.loadGraphic(cl,(EjbWithId)status);
			if(supportsGraphic)
			{
				graphic = ((EjbWithGraphic<G>)status).getGraphic();
				if(debugSave){logger.info("Saved "+graphic.getClass().getSimpleName()+" "+graphic.toString());}
			}
			if(supportsFigure){reloadFigures();}
			if(debugSave){logger.info("Saved "+status.getClass().getSimpleName()+" "+status.toString());}
			
			updateAppScopeBean(fUtils,status);
			selectCategory(false);
			bMessage.growlSuccessSaved();
		}
		catch (JeeslConstraintViolationException e)
		{
			logger.error(JeeslConstraintViolationException.class.getSimpleName()+" "+e.getMessage());
			bMessage.errorConstraintViolationInUse();
		}
		catch (JeeslLockingException e)
		{
			logger.error(JeeslLockingException.class.getSimpleName()+" "+e.getMessage());
			bMessage.errorConstraintViolationInUse();
		}
	}
	
	public void rm() throws ClassNotFoundException
	{
		try
		{
			fUtils.rm((EjbRemoveable)status);
			updateAppScopeBean(fUtils,status);
			status=null;
			selectCategory();
			bMessage.growlSuccessRemoved();
		}
		catch (JeeslConstraintViolationException e)
		{
			bMessage.errorConstraintViolationInUse();
		}
	}
	
	public void cancelStatus() {reset(true,true);}
	public void cancelFigure() {reset(false,true);reloadFigures();}
	private void reset(boolean rStatus, boolean rFigure)
	{
		if(rStatus){status=null;}
		if(rFigure){figure=null;}
	}
	
	protected void updateAppScopeBean(JeeslFacade facade, Object o){}
	
	public void reorder() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fUtils, items);}
	public void reorderFigures() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fUtils,figures);}
	
	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();
		logger.info("Received file with a size of " +file.getSize());
		((EjbWithGraphic<G>)status).getGraphic().setData(file.getContents());  
	}
	
//	@Override
	@SuppressWarnings("unchecked")
	public void changeGraphicType()
	{
		((EjbWithGraphic<G>)status).getGraphic().setType(fUtils.find(fbSvg.getClassGraphicType(), ((EjbWithGraphic<G>)status).getGraphic().getType()));
		logger.info("changeGraphicType to "+((EjbWithGraphic<G>)status).getGraphic().getType().getCode());
	}
	
	private void reloadFigures()
	{
		figures = fUtils.allForParent(fbSvg.getClassFigure(),graphic);
	}
	
	public void addFigure()
	{
		logger.info("Add "+fbSvg.getClassFigure().getSimpleName());
		figure = efFigure.build(graphic);
	}
	
	public void selectFigure()
	{
		logger.info("Select "+figure.toString());
	}
	
	public void saveFigure() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("Select "+figure.toString());
		figure.setStyle(fUtils.find(fbSvg.getClassFigureStyle(),figure.getStyle()));
		figure = fUtils.save(figure);
		reloadFigures();
	}
	
	public void deleteFigure() throws JeeslConstraintViolationException, JeeslLockingException
	{
		fUtils.rm(figure);
		reset(false,true);
		reloadFigures();
	}
}