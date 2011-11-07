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
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.access.AclContainer;
import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.access.RoleCategory;
import net.sf.ahtutils.xml.access.Roles;
import net.sf.ahtutils.xml.access.Usecase;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AclInitRole <	S extends UtilsStatus<L>,
							L extends UtilsLang,
							D extends UtilsDescription,
							CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
							U extends UtilsAclUsecase<L,D,CU,U>,
							CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
							R extends UtilsAclRole<L,D,CU,CR,U,R>>
{
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<CU> categoryUsecaseClass;
    final Class<U> usecaseClass;
    
    final Class<CR> categoryRoleClass;
    final Class<R> roleClass;
	
	static Log logger = LogFactory.getLog(AclInitRole.class);
	
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <S extends UtilsStatus<L>,L extends UtilsLang,D extends UtilsDescription,
				   CU extends UtilsAclCategoryUsecase<L,D,CU,U>, U extends UtilsAclUsecase<L,D,CU,U>,
				   CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,R extends UtilsAclRole<L,D,CU,CR,U,R>>
		AclInitRole<S, L, D,CU,U,CR,R>
		factory(final Class<S> statusClass,final Class<L> langClass,final Class<D> descriptionClass,
				final Class<CU> categoryUsecaseClass,final Class<U> usecaseClass,
				final Class<CR> categoryRoleClass,final Class<R> roleClass,
				AhtAclFacade fAcl)
	{
		return new AclInitRole<S, L, D, CU, U,CR,R>(statusClass,langClass,descriptionClass,categoryUsecaseClass,usecaseClass,categoryRoleClass,roleClass,fAcl);
	}
	
	public AclInitRole(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass,
			final Class<CU> categoryUsecaseClass, final Class<U> usecaseClass,
			final Class<CR> categoryRoleClass,final Class<R> roleClass,
			AhtAclFacade fAcl)
	{       
		this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryUsecaseClass = categoryUsecaseClass;
        this.usecaseClass = usecaseClass;
        this.categoryRoleClass=categoryRoleClass;
        this.roleClass=roleClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuRoleCategory(String xmlFile) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		AhtDbEjbUpdater<CR> updateRoleCategory = AhtDbEjbUpdater.createFactory(categoryRoleClass);
		AhtDbEjbUpdater<R> updateRole = AhtDbEjbUpdater.createFactory(roleClass);
		
		updateRoleCategory.dbEjbs(fAcl.allAclRoleCategory(categoryRoleClass));
		updateRole.dbEjbs(fAcl.allAclRole(roleClass));
		
		logger.debug("i/u "+RoleCategory.class.getSimpleName());
		AclContainer aclContainer = (AclContainer)JaxbUtil.loadJAXB(xmlFile, AclContainer.class);
		for(RoleCategory category : aclContainer.getRoleCategory())
		{
			updateRoleCategory.actualAdd(category.getCode());
			CR aclRoleCategory;
			try
			{
				aclRoleCategory = fAcl.fRoleCategoryByCode(categoryRoleClass,category.getCode());
				
				Map<String,L> langMap = aclRoleCategory.getName();
				Map<String,D> descMap = aclRoleCategory.getDescription();
				aclRoleCategory.setName(null);
				aclRoleCategory.setDescription(null);
				
				try{aclRoleCategory=(CR)fAcl.updateAhtUtilsStatus(aclRoleCategory);}
				catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(lang);}
					catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(desc);}
					catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				}
			}
			catch (AhtUtilsNotFoundException e)
			{
				try
				{
					aclRoleCategory = categoryRoleClass.newInstance();
					aclRoleCategory.setCode(category.getCode());
					aclRoleCategory = (CR)fAcl.persistAhtUtilsStatus(aclRoleCategory);
					
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclRoleCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclRoleCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclRoleCategory=(CR)fAcl.updateAhtUtilsStatus(aclRoleCategory);
				if(category.isSetRoles() && category.getRoles().isSetRole())
				{
					initUpdateRole(aclRoleCategory, category.getRoles(), false);
				}
			}
			catch (AhtUtilsContraintViolationException e) {logger.error(e);}
			catch (InstantiationException e) {logger.error(e);}
			catch (IllegalAccessException e) {logger.error(e);}
			catch (AhtUtilsIntegrityException e) {logger.error(e);}
		}
/*		for(RoleCategory category : aclContainer.getRoleCategory())
		{
			try
			{
				AclRoleCategory aclRoleCategory = fAcl.fRoleCategoryByCode(AclRoleCategory.class,category.getCode());
				if(category.isSetRoles() && category.getRoles().isSetRole())
				{
					initUpdateRole(aclRoleCategory, category.getRoles(), true);
				}
			}
			catch (AhtUtilsNotFoundException e) {logger.error(e);}
		}
*/ 
		updateRoleCategory.remove(fAcl);
	}
	

	private void initUpdateRole(CR aclRoleCategory, Roles roles, boolean refRoles) throws AhtUtilsConfigurationException
	{
		logger.warn("No removing of Roles");
		for(Role role : roles.getRole())
		{
			initUpdateRole(aclRoleCategory, role, refRoles);
		}
	}
	
	private void initUpdateRole(CR roleCategory, Role role, boolean refRoles) throws AhtUtilsConfigurationException
	{
		logger.trace("initUpdateRole "+roleCategory.getCode()+"-"+role.getCode()+" (refRolse="+refRoles+")");
		R aclRole;
		try
		{
			aclRole = fAcl.fRoleByCode(roleClass,role.getCode());
			
			if(!refRoles)
			{
				Map<String,L> langMap = aclRole.getName();
				Map<String,D> descMap = aclRole.getDescription();
				aclRole.setName(null);
				aclRole.setDescription(null);
				aclRole.setUsecases(null);
				
				try{aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);}
				catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(lang);}
					catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fAcl.rmAhtUtilsEntity(desc);}
					catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				}
			}
		}
		catch (AhtUtilsNotFoundException e)
		{
			if(refRoles){logger.error("Ref.Role "+role.getCode()+" not found! Exit");System.exit(-1);}
			try
			{
				aclRole = roleClass.newInstance();
				aclRole.setCode(role.getCode());
				aclRole = (R)fAcl.persistAhtUtilsStatus(aclRole);
			}
			catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			
		}
		
		if(!refRoles)
		{
			try
			{
				aclRole.setName(ejbLangFactory.getLangMap(role.getLangs()));
//				aclRole.setDescription(ejbFactory.getDescriptionMap(role.getDescriptions()));
				aclRole.setCategory(roleCategory);
				
				if(role.isSetUsecases() && role.getUsecases().isSetUsecase())
				{
					for(Usecase usecase : role.getUsecases().getUsecase())
					{
						try {aclRole.getUsecases().add(fAcl.fUsecaseByCode(usecaseClass,usecase.getCode()));}
						catch (AhtUtilsNotFoundException e) {logger.error(e);}
					}
				}
				aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);
			}
			catch (AhtUtilsContraintViolationException e) {logger.error(e);}
			catch (InstantiationException e) {logger.error(e);}
			catch (IllegalAccessException e) {logger.error(e);}
			catch (AhtUtilsIntegrityException e) {logger.error(e);}
		}
		else
		{
			logger.debug("Ref.Roles for "+aclRole.getCode());
			aclRole.setRoles(null);
			try{aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);}
			catch (AhtUtilsContraintViolationException e) {logger.error(e);}
			if(role.isSetRoles() && role.getRoles().isSetRole())
			{
				for(Role subRole : role.getRoles().getRole())
				{
					try
					{
						R aclSubRole = fAcl.fRoleByCode(roleClass,subRole.getCode());
						aclRole.getRoles().add(aclSubRole);
					}
					catch (AhtUtilsNotFoundException e) {{logger.error("Sub.Role "+subRole.getCode()+" not found! Exit.");System.exit(-1);}}
				}
				try{aclRole=(R)fAcl.updateAhtUtilsStatus(aclRole);}
				catch (AhtUtilsContraintViolationException e) {logger.error(e);}
			}
		}
	}
}