package net.sf.ahtutils.db.ejb.acl;

import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.AhtAclFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AclInitRoles <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsAclCategoryProjectRole<L,D,C,R>,
								R extends UtilsAclRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitRoles.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<C> categoryClass;
    final Class<R> roleClass;
	
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
		AclInitRoles<L,D,C,R>
		factory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{
		return new AclInitRoles<L,D,C,R>(langClass, descriptionClass, categoryClass, roleClass,fAcl);
	}
	
	public AclInitRoles(final Class<L> langClass, final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{       
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryClass = categoryClass;
        this.roleClass = roleClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuRoles(Access access) throws AhtUtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		AhtDbEjbUpdater<C> updateCategory = AhtDbEjbUpdater.createFactory(categoryClass);
		AhtDbEjbUpdater<R> updateRole = AhtDbEjbUpdater.createFactory(roleClass);
		
		updateCategory.dbEjbs(fAcl.all(categoryClass));
		updateRole.dbEjbs(fAcl.all(roleClass));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fAcl.fAhtUtilsByCode(categoryClass,category.getCode());
				
				Map<String,L> langMap = aclCategory.getName();
				Map<String,D> descMap = aclCategory.getDescription();
				aclCategory.setName(null);
				aclCategory.setDescription(null);
				
				try{aclCategory=(C)fAcl.updateAhtUtilsStatus(aclCategory);}
				catch (UtilsContraintViolationException e) {logger.error("",e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(lang);}
					catch (UtilsContraintViolationException e) {logger.error("",e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(desc);}
					catch (UtilsContraintViolationException e) {logger.error("",e);}
				}
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					aclCategory = categoryClass.newInstance();
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fAcl.persistAhtUtilsStatus(aclCategory);
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclCategory=(C)fAcl.updateAhtUtilsStatus(aclCategory);
				
				if(category.isSetRoles() && category.getRoles().isSetRole())
				{
					for(Role role : category.getRoles().getRole())
					{
						updateRole.actualAdd(role.getCode());
						initUpdateProjectRole(aclCategory, role);
					}
				}
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fAcl);
		updateRole.remove(fAcl);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	public void initUpdateProjectRole(C category, Role role) throws AhtUtilsConfigurationException
	{
		R aclRole;
		try
		{
			aclRole = fAcl.fAhtUtilsByCode(roleClass,role.getCode());
			
			Map<String,L> langMap = aclRole.getName();
			Map<String,D> descMap = aclRole.getDescription();
			aclRole.setName(null);
			aclRole.setDescription(null);
			
			try{aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			
			for(L lang : langMap.values())
			{
				try {fAcl.rmAhtUtilsEntity(lang);}
				catch (UtilsContraintViolationException e) {logger.error("",e);}
			}
			
			for(D desc : descMap.values())
			{
				try {fAcl.rmAhtUtilsEntity(desc);}
				catch (UtilsContraintViolationException e) {logger.error("",e);}
			}
		}
		catch (UtilsNotFoundException e)
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
			catch (UtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			aclRole.setName(ejbLangFactory.getLangMap(role.getLangs()));
//			aclRole.setDescription(ejbFactory.getDescriptionMap(role.getDescriptions()));
			aclRole.setCategory(category);
			aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);
		}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (UtilsIntegrityException e) {logger.error("",e);}
	}
}