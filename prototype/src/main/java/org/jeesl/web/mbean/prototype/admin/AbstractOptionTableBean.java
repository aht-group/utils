package org.jeesl.web.mbean.prototype.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFigureFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.interfaces.model.system.with.EjbWithGraphicFigure;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithImage;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImage;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImageAlt;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.exlp.util.io.StringUtil;

public class AbstractOptionTableBean <L extends UtilsLang, D extends UtilsDescription,
										G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
			extends AbstractAdminBean<L,D>
			implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOptionTableBean.class);
	private static final long serialVersionUID = 1L;

	protected UtilsFacade fUtils;
	
	protected boolean allowSvg; public boolean isAllowSvg() {return allowSvg;}
	
	protected boolean supportsSymbol; public boolean getSupportsSymbol(){return supportsSymbol;}
	protected boolean supportsImage; public boolean getSupportsImage() {return supportsImage;}
	protected boolean supportsGraphic; public boolean getSupportsGraphic() {return supportsGraphic;}
	protected boolean supportsFigure; public boolean isSupportsFigure() {return supportsFigure;}

	protected long index;
	protected Map<Long,Boolean> allowAdditionalElements; public Map<Long, Boolean> getAllowAdditionalElements(){return allowAdditionalElements;}
	
	protected Object category; public Object getCategory() {return category;} public void setCategory(Object category) {this.category = category;}
	protected Object status; public Object getStatus() {return status;} public void setStatus(Object status) {this.status = status;}
	private G graphic; public G getGraphic() {return graphic;} public void setGraphic(G graphic) {this.graphic = graphic;}

	@SuppressWarnings("rawtypes")
	protected Class cl,clParent;
	
	protected List<EjbWithPosition> categories; public List<EjbWithPosition> getCategories(){return categories;}
	protected List<EjbWithPosition> parents; public List<EjbWithPosition> getParents(){return parents;}
	protected List<EjbWithPosition> items; public List<EjbWithPosition> getItems() {return items;}
	private List<GT> graphicTypes; public List<GT> getGraphicTypes() {return graphicTypes;}
	private List<FS> graphicStyles; public List<FS> getGraphicStyles() {return graphicStyles;}
	private List<F> figures; public List<F> getFigures() {return figures;}
	private F figure; public F getFigure() {return figure;} public void setFigure(F figure) {this.figure = figure;}

	protected final Class<?> cStatus;
	
	private final Class<G> cG;
	private final Class<GT> cGT;
	private final Class<F> cF;
	private final Class<FS> cFS;
	
	protected long parentId; public long getParentId(){return parentId;}public void setParentId(long parentId){this.parentId = parentId;}
	
	protected final EjbGraphicFactory<L,D,G,GT,F,FS> efGraphic;
	private final EjbGraphicFigureFactory<L,D,G,GT,F,FS> efFigure;
	
	public AbstractOptionTableBean(final Class<L> cL, final Class<D> cD, Class<G> cG, Class<GT> cGT, final Class<F> cF, final Class<FS> cFS, final Class<?> cStatus)
	{
		super(cL,cD);
		this.cG=cG;
		this.cGT=cGT;
		this.cF=cF;
		this.cFS=cFS;
		this.cStatus=cStatus;
		
		SvgFactoryBuilder<L,D,G,GT,F,FS> ffSvg = SvgFactoryBuilder.factory(cL,cD,cG,cF,cFS);
		efGraphic = ffSvg.efGraphic();
		
		efFigure = new EjbGraphicFigureFactory<L,D,G,GT,F,FS>(cF);
		
		index=1;
		
		hasDeveloperAction = false;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		status = null;
		allowAdditionalElements = new Hashtable<Long,Boolean>();
		
		categories = new ArrayList<EjbWithPosition>();
	}
	
	protected void initUtils(JeeslTranslationBean bTranslation, UtilsFacade fUtils, FacesMessageBean bMessage)
	{
		String[] langs = bTranslation.getLangKeys().toArray(new String[0]);
		super.initAdmin(langs,cL,cD,bMessage);
		this.fUtils=fUtils;
			
		graphicTypes = fUtils.allOrderedPositionVisible(cGT);
		graphicStyles = fUtils.allOrderedPositionVisible(cFS);
	}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		super.updateSecurity2(jsfSecurityHandler, viewCode);
	}
	
	protected void updateUiForCategory()
	{
		supportsImage = UtilsWithImage.class.isAssignableFrom(cl);
		supportsGraphic = EjbWithGraphic.class.isAssignableFrom(cl);
		supportsSymbol = UtilsWithSymbol.class.isAssignableFrom(cl);
		supportsFigure = EjbWithGraphicFigure.class.isAssignableFrom(cl);
	}
	
	protected void debugUi(boolean debug)
	{
		super.debugUi(debug);
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Option Tables Settings");
			logger.info("\tImage? "+supportsImage);
			logger.info("\tGraphic? "+supportsGraphic);
			logger.info("\tSymbol? "+supportsSymbol);
			logger.info("\tFigure? "+supportsFigure);
		}
	}
	
	public void selectCategory() throws ClassNotFoundException{selectCategory(true);}
	@SuppressWarnings("unchecked")
	public void selectCategory(boolean reset) throws ClassNotFoundException
	{
		if(category==null) {logger.error("selectCategory, but category is NULL");}
		
		StringBuilder sb = new StringBuilder();
		sb.append("selectCategory");
		sb.append(" ").append(((EjbWithCode)category).getCode());
		sb.append(" (").append(((EjbWithImageAlt)category).getImageAlt()).append(")");
		sb.append(" allowAdditionalElements:").append(allowAdditionalElements.get(((EjbWithId)category).getId()));
		logger.info(sb.toString());
		
		cl = Class.forName(((EjbWithImage)category).getImage());
		updateUiForCategory();
		
		uiAllowAdd = allowAdditionalElements.get(((EjbWithId)category).getId()) || hasDeveloperAction;
		
		if(((EjbWithImageAlt)category).getImageAlt()!=null)
		{
            clParent = Class.forName(((EjbWithImageAlt)category).getImageAlt()).asSubclass(cStatus);
            parents = fUtils.all(clParent);
            logger.info(cl.getSimpleName()+" "+parents.size());
		}
		else
		{
			clParent=null;
			parents=null;
		}
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
	public void add() throws UtilsConstraintViolationException, InstantiationException, IllegalAccessException, UtilsNotFoundException
	{
		logger.debug("add");
		uiAllowCode=true;
		
		status = cl.newInstance();
		((EjbWithId)status).setId(0);
		((EjbWithCode)status).setCode("enter code");
		((EjbWithLang<L>)status).setName(efLang.createEmpty(langs));
		((EjbWithDescription<D>)status).setDescription(efDescription.createEmpty(langs));
		
		if(supportsGraphic)
		{
			GT type = fUtils.fByCode(cGT, JeeslGraphicType.Code.symbol.toString());
			FS style = fUtils.fByCode(cFS, JeeslGraphicStyle.Code.circle.toString());
			graphic = efGraphic.buildSymbol(type, style);
			((EjbWithGraphic<L,D,G,GT,F,FS>)status).setGraphic(graphic);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void selectStatus() throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		figures = null; figure=null;
		status = fUtils.find(cl,(EjbWithId)status);
		status = fUtils.load(cl,(EjbWithId)status);
		logger.debug("selectStatus");
		status = efLang.persistMissingLangs(fUtils,langs,(EjbWithLang)status);
		status = efDescription.persistMissingLangs(fUtils,langs,(EjbWithDescription)status);
		
		if(((EjbWithParent)status).getParent()!=null)
		{
			parentId=((EjbWithParent)status).getParent().getId();
		}
		
		if(supportsGraphic)
		{
			if(((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic()==null)
			{
				logger.info("Need to create a graphic entity for this status");
				GT type = fUtils.fByCode(cGT, JeeslGraphicType.Code.symbol.toString());
				FS style = fUtils.fByCode(cFS, JeeslGraphicStyle.Code.circle.toString());
				graphic = fUtils.persist(efGraphic.buildSymbol(type, style));
				((EjbWithGraphic<L,D,G,GT,F,FS>)status).setGraphic(graphic);
				status = fUtils.update(status);
			}
			graphic = ((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic();
			
			if(supportsFigure){reloadFigures();}
		}
		
		uiAllowCode = hasDeveloperAction || hasAdministratorAction;
		if(hasDeveloperAction){uiAllowCode=true;}
		else if(status instanceof UtilsStatusFixedCode)
		{
			for(String fixed : ((UtilsStatusFixedCode)status).getFixedCodes())
			{
				if(fixed.equals(((UtilsStatus)status).getCode()))
				{
					uiAllowCode=false;
				}
			}
		}
				
		debugUi(false);
		pageFlowPrimarySelect(status);
	}

	@SuppressWarnings("unchecked")
	public void save() throws ClassNotFoundException, UtilsNotFoundException
    {
		boolean debugSave=true;
		try
		{
            if(clParent!=null && parentId>0)
            {
            	((EjbWithParent)status).setParent((EjbWithCode)fUtils.find(clParent, parentId));
            }
        	if(supportsGraphic && graphic!=null)
            {
        		graphic.setType(fUtils.find(cGT, ((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().getType()));
            	if(graphic.getStyle()!=null){graphic.setStyle(fUtils.find(cFS, ((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().getStyle()));}
        		
//            	if(debugSave){logger.info("Saving "+graphic.getClass().getSimpleName()+" "+graphic.toString());}
//           	graphic = fUtils.save(graphic);
            	((EjbWithGraphic<L,D,G,GT,F,FS>)status).setGraphic(graphic);
//            	if(debugSave){logger.info("Saved "+graphic.getClass().getSimpleName()+" "+graphic.toString());}
            }

        	if(debugSave){logger.info("Saving "+status.getClass().getSimpleName()+" "+status.toString());}
			status = fUtils.save((EjbSaveable)status);
			status = fUtils.load(cl,(EjbWithId)status);
			if(supportsGraphic)
			{
				graphic = ((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic();
				if(debugSave){logger.info("Saved "+graphic.getClass().getSimpleName()+" "+graphic.toString());}
			}
			if(supportsFigure){reloadFigures();}
			if(debugSave){logger.info("Saved "+status.getClass().getSimpleName()+" "+status.toString());}
			
			
			
			updateAppScopeBean2(status);
			selectCategory(false);
			bMessage.growlSuccessSaved();
		}
		catch (UtilsConstraintViolationException e)
		{
			logger.error(UtilsConstraintViolationException.class.getSimpleName()+" "+e.getMessage());
			bMessage.errorConstraintViolationInUse();
		}
		catch (UtilsLockingException e)
		{
			logger.error(UtilsLockingException.class.getSimpleName()+" "+e.getMessage());
			bMessage.errorConstraintViolationInUse();
		}
	}
	
	public void rm() throws ClassNotFoundException
	{
		try
		{
			fUtils.rm((EjbRemoveable)status);
			updateAppScopeBean2(status);
			status=null;
			selectCategory();
			bMessage.growlSuccessRemoved();
		}
		catch (UtilsConstraintViolationException e)
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
	
	protected void updateAppScopeBean2(Object o){}
	
	public void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fUtils, items);}
	public void reorderFigures() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fUtils,figures);}
	
	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();
		logger.info("Received file with a size of " +file.getSize());
		((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().setData(file.getContents());  
	}
	
//	@Override
	@SuppressWarnings("unchecked")
	public void changeGraphicType()
	{
		((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().setType(fUtils.find(cGT, ((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().getType()));
		logger.info("changeGraphicType to "+((EjbWithGraphic<L,D,G,GT,F,FS>)status).getGraphic().getType().getCode());
	}
	
	private void reloadFigures()
	{
		figures = fUtils.allForParent(cF,graphic);
	}
	
	public void addFigure()
	{
		logger.info("Add "+cF.getSimpleName());
		figure = efFigure.build(graphic);
	}
	
	public void selectFigure()
	{
		logger.info("Select "+figure.toString());
	}
	
	public void saveFigure() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Select "+figure.toString());
		figure.setStyle(fUtils.find(cFS,figure.getStyle()));
		figure = fUtils.save(figure);
		reloadFigures();
	}
	
	public void deleteFigure() throws UtilsConstraintViolationException, UtilsLockingException
	{
		fUtils.rm(figure);
		reset(false,true);
		reloadFigures();
	}
	
	//Revision
	public void pageFlowPrimarySelect(Object revision) {}
	public void pageFlowPrimaryCancel() {}
	public void pageFlowPrimarySave(Object revision) {}
	public void pageFlowPrimaryAdd() {}
}