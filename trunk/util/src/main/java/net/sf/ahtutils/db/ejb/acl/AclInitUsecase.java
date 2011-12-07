package net.sf.ahtutils.db.ejb.acl;

import java.io.FileNotFoundException;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.AhtAclFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AclInitUsecase <S extends UtilsStatus<L>,
							L extends UtilsLang,
							D extends UtilsDescription,
							CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
							U extends UtilsAclUsecase<L,D,CU,U>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitUsecase.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<CU> categoryUsecaseClass;
    final Class<U> usecaseClass;
		
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <S extends UtilsStatus<L>,
				   L extends UtilsLang,
				   D extends UtilsDescription,
				   CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
				   U extends UtilsAclUsecase<L,D,CU,U>>
		AclInitUsecase<S, L, D,CU,U>
		factory(final Class<S> statusClass,
				final Class<L> langClass,
				final Class<D> descriptionClass,
				final Class<CU> categoryUsecaseClass,
				final Class<U> usecaseClass,
				AhtAclFacade fAcl)
	{
		return new AclInitUsecase<S, L, D, CU, U>(statusClass, langClass, descriptionClass, categoryUsecaseClass, usecaseClass,fAcl);
	}
	
	public AclInitUsecase(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass,
			final Class<CU> categoryUsecaseClass, final Class<U> usecaseClass,
			AhtAclFacade fAcl)
	{       
		this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryUsecaseClass = categoryUsecaseClass;
        this.usecaseClass = usecaseClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuUsecases(Access access) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		AhtDbEjbUpdater<CU> ejbUccUpdate = AhtDbEjbUpdater.createFactory(categoryUsecaseClass);
		AhtDbEjbUpdater<U> ejbUcUpdate = AhtDbEjbUpdater.createFactory(usecaseClass);
		
		ejbUccUpdate.dbEjbs(fAcl.all(categoryUsecaseClass));
		ejbUcUpdate.dbEjbs(fAcl.all(usecaseClass));

		for(Category category : access.getCategory())
		{
			logger.debug("Processing category: "+category.getCode());
			ejbUccUpdate.actualAdd(category.getCode());
			
			CU usecaseCategory;
			try
			{
				usecaseCategory = fAcl.fAhtUtilsByCode(categoryUsecaseClass, category.getCode());
				
				logger.trace("removing existing langs: "+category.getCode());
				Map<String,L> langMap = usecaseCategory.getName();
				Map<String,D> descMap = usecaseCategory.getDescription();
				usecaseCategory.setName(null);
				usecaseCategory.setDescription(null);
				
				try{usecaseCategory=(CU)fAcl.updateAhtUtilsStatus(usecaseCategory);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(lang);}
					catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(desc);}
					catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				}
				logger.trace("removed existing langs: "+category.getCode());
			}
			catch (AhtUtilsNotFoundException e)
			{
				try
				{
					usecaseCategory = categoryUsecaseClass.newInstance();
					usecaseCategory.setCode(category.getCode());
					usecaseCategory = fAcl.persistAhtUtilsStatus(usecaseCategory);
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				usecaseCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				usecaseCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				usecaseCategory=fAcl.updateAhtUtilsStatus(usecaseCategory);
				
				if(category.isSetUsecases() && category.getUsecases().isSetUsecase())
				{
					for(Usecase usecase : category.getUsecases().getUsecase())
					{
						ejbUcUpdate.actualAdd(usecase.getCode());
						initUpdateUsecase(usecaseCategory, usecase);
					}
				}
			}
			catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (AhtUtilsIntegrityException e) {logger.error("",e);}
		}
		
		ejbUcUpdate.remove(fAcl);
		ejbUccUpdate.remove(fAcl);
		
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	public void initUpdateUsecase(CU usecaseCategory, Usecase usecase) throws AhtUtilsConfigurationException
	{
		U aclUsecase;
		try
		{
			aclUsecase = fAcl.fAhtUtilsByCode(usecaseClass, usecase.getCode());
			
			Map<String,L> langMap = aclUsecase.getName();
			Map<String,D> descMap = aclUsecase.getDescription();
			aclUsecase.setName(null);
			aclUsecase.setDescription(null);
			
			try{aclUsecase=fAcl.updateAhtUtilsStatus(aclUsecase);}
			catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			
			for(L lang : langMap.values())
			{
				try {fAcl.rmAhtUtilsEntity(lang);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			}
			
			for(D desc : descMap.values())
			{
				try {fAcl.rmAhtUtilsEntity(desc);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			}
		}
		catch (AhtUtilsNotFoundException e)
		{
			try
			{
				aclUsecase = usecaseClass.newInstance();
				aclUsecase.setCode(usecase.getCode());
				aclUsecase = fAcl.persistAhtUtilsStatus(aclUsecase);
			}
			catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			aclUsecase.setName(ejbLangFactory.getLangMap(usecase.getLangs()));
//			aclUsecase.setDescription(ejbFactory.getDescriptionMap(usecase.getDescriptions()));
			aclUsecase.setCategory(usecaseCategory);
			aclUsecase=fAcl.updateAhtUtilsStatus(aclUsecase);
		}
		catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (AhtUtilsIntegrityException e) {logger.error("",e);}
	}
}