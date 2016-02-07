package net.sf.ahtutils.prototype.web.mbean.admin.system.io;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.system.io.EjbIoTemplateFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsIoFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.io.UtilsIoTemplate;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoTemplateBean <L extends UtilsLang,D extends UtilsDescription,
											IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
											IOTT extends UtilsStatus<IOTT,L,D>,
											IOTC extends UtilsStatus<IOTC,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoTemplateBean.class);
	
	private UtilsIoFacade<L,D,IOT,IOTT,IOTC> fIo;
	
	private Class<IOT> cTemplate;
	private Class<IOTT> cTemplateType;
	private Class<IOTC> cTemplateCategory;
	
	private IOT template; public IOT getTemplate() {return template;} public void setTemplate(IOT template) {this.template = template;}

	private List<IOT> templates; public List<IOT> getTemplates() {return templates;}
	private List<IOTT> types; public List<IOTT> getTypes() {return types;}
	private List<IOTC> categories; public List<IOTC> getCategories() {return categories;}

	private EjbIoTemplateFactory<L,D,IOT,IOTT,IOTC> efTemplate;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsIoFacade<L,D,IOT,IOTT,IOTC> fIo, final Class<L> cLang, final Class<D> cDescription,Class<IOT> cTemplate, Class<IOTT> cTemplateType, Class<IOTC> cTemplateCategory)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fIo=fIo;
		this.cTemplate=cTemplate;
		this.cTemplateType=cTemplateType;
		this.cTemplateCategory=cTemplateCategory;
		
		efTemplate = EjbIoTemplateFactory.factory(cTemplate);
		
		types = fIo.allOrderedPositionVisible(cTemplateType);
		categories = fIo.allOrderedPositionVisible(cTemplateCategory);
		
		allowSave=true;
	}

	private void reloadTemplates()
	{
		templates = fIo.allOrderedPositionVisible(cTemplate);
//		Collections.sort(entities, comparatorEntity);
	}
	
	public void addTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		template = efTemplate.build(null);
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
	}
	
	private void reloadTemplate()
	{

	}
	
	public void selectTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
		template = fIo.find(cTemplate, template);
		template = efLang.persistMissingLangs(fIo,langs,template);
		template = efDescription.persistMissingLangs(fIo,langs,template);
		reloadTemplate();
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(template));}
		template.setType(fIo.find(cTemplateType, template.getType()));
		template.setCategory(fIo.find(cTemplateCategory, template.getCategory()));
		template = fIo.save(template);
		reloadTemplates();
		reloadTemplate();
		bMessage.growlSuccessSaved();
	}
	
	public void rmEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(template));}
		fIo.rm(template);
		template=null;

		bMessage.growlSuccessRemoved();
		reloadTemplates();
	}
	
	public void cancelTemplate()
	{
		template = null;
	}
	
//	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}
//	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
//	protected void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, entityMappings);}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String action)
	{
		allowSave = jsfSecurityHandler.allow(action);

		if(logger.isTraceEnabled())
		{
			logger.info(allowSave+" allowSave ("+action+")");
		}
	}
}