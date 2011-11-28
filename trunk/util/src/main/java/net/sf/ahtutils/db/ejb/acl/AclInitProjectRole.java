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
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.AclContainer;
import net.sf.ahtutils.xml.access.ProjectRole;
import net.sf.ahtutils.xml.access.ProjectRoleCategory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AclInitProjectRole <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsAclCategoryProjectRole<L,D,C,R>,
								R extends UtilsAclProjectRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitProjectRole.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<C> categoryClass;
    final Class<R> roleClass;
	
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
		AclInitProjectRole<L,D,C,R>
		factory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{
		return new AclInitProjectRole<L,D,C,R>(langClass, descriptionClass, categoryClass, roleClass,fAcl);
	}
	
	public AclInitProjectRole(final Class<L> langClass, final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{       
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryClass = categoryClass;
        this.roleClass = roleClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	// Project Roles
	public void initUpdateProjectRoleCategories(String xmlFile) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		AhtDbEjbUpdater<C> updateCategory = AhtDbEjbUpdater.createFactory(categoryClass);
		AhtDbEjbUpdater<R> updateRole = AhtDbEjbUpdater.createFactory(roleClass);
		
		updateCategory.dbEjbs(fAcl.allAclProjectRoleCategory(categoryClass));
		updateRole.dbEjbs(fAcl.allAclProjectRole(roleClass));
		
		logger.trace("i/u "+ProjectRoleCategory.class.getSimpleName()+" with "+xmlFile);
		AclContainer aclContainer = (AclContainer)JaxbUtil.loadJAXB(xmlFile, AclContainer.class);

		for(ProjectRoleCategory category : aclContainer.getProjectRoleCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fAcl.fProjectRoleCategoryByCode(categoryClass,category.getCode());
				
				Map<String,L> langMap = aclCategory.getName();
				Map<String,D> descMap = aclCategory.getDescription();
				aclCategory.setName(null);
				aclCategory.setDescription(null);
				
				try{aclCategory=(C)fAcl.updateAhtUtilsStatus(aclCategory);}
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
					aclCategory = categoryClass.newInstance();
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fAcl.persistAhtUtilsStatus(aclCategory);
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclCategory=(C)fAcl.updateAhtUtilsStatus(aclCategory);
				
				if(category.isSetProjectRoles() && category.getProjectRoles().isSetProjectRole())
				{
					for(ProjectRole role : category.getProjectRoles().getProjectRole())
					{
						updateRole.actualAdd(role.getCode());
						initUpdateProjectRole(aclCategory, role);
					}
				}
				
			}
			catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (AhtUtilsIntegrityException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fAcl);
		updateRole.remove(fAcl);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	public void initUpdateProjectRole(C category, ProjectRole role) throws AhtUtilsConfigurationException
	{
		R aclRole;
		try
		{
			aclRole = fAcl.fProjectRoleByCode(roleClass,role.getCode());
			
			Map<String,L> langMap = aclRole.getName();
			Map<String,D> descMap = aclRole.getDescription();
			aclRole.setName(null);
			aclRole.setDescription(null);
			
			try{aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);}
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
				aclRole = roleClass.newInstance();
				aclRole.setCategory(category);
				aclRole.setCode(role.getCode());
				aclRole = (R)fAcl.persistAhtUtilsStatus(aclRole);
			}
			catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			aclRole.setName(ejbLangFactory.getLangMap(role.getLangs()));
//			aclRole.setDescription(ejbFactory.getDescriptionMap(role.getDescriptions()));
			aclRole.setCategory(category);
			aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);
		}
		catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (AhtUtilsIntegrityException e) {logger.error("",e);}
	}
}