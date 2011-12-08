package net.sf.ahtutils.db.ejb.security;

import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.AhtSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityInitRoles <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitRoles.class);
	
    final Class<L> cL;
    final Class<D> cD;
    final Class<C> cC;
    final Class<R> cR;
    final Class<V> cV;
    final Class<U> cU;
    final Class<A> cA;
	
	private AhtSecurityFacade fSecurity;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <L extends UtilsLang,
				   D extends UtilsDescription, 
				   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
				   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
				   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
				   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
				   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		SecurityInitRoles<L,D,C,R,V,U,A>
		factory(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, AhtSecurityFacade fAcl)
	{
		return new SecurityInitRoles<L,D,C,R,V,U,A>(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public SecurityInitRoles(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,AhtSecurityFacade fAcl)
	{       
        this.cL = cL;
        this.cD = cD;
        this.cC = cC;
        this.cR = cR;
        this.cV = cV;
        this.cU = cU;
        this.cA = cA;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
	}
	
	public void iuRoles(Access access) throws AhtUtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		AhtDbEjbUpdater<C> updateCategory = AhtDbEjbUpdater.createFactory(cC);
		AhtDbEjbUpdater<R> updateRole = AhtDbEjbUpdater.createFactory(cR);
		
		updateCategory.dbEjbs(fSecurity.all(cC));
		updateRole.dbEjbs(fSecurity.all(cR));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fSecurity.fAhtUtilsByCode(cC,category.getCode());
				
				Map<String,L> langMap = aclCategory.getName();
				Map<String,D> descMap = aclCategory.getDescription();
				aclCategory.setName(null);
				aclCategory.setDescription(null);
				
				try{aclCategory=(C)fSecurity.updateAhtUtilsStatus(aclCategory);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				
				for(L lang : langMap.values())
				{
					try {fSecurity.rmAhtUtilsEntity(lang);}
					catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fSecurity.rmAhtUtilsEntity(desc);}
					catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
				}
			}
			catch (AhtUtilsNotFoundException e)
			{
				try
				{
					aclCategory = cC.newInstance();
					aclCategory.setType(UtilsSecurityCategory.Type.role.toString());
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fSecurity.persistAhtUtilsStatus(aclCategory);
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclCategory=(C)fSecurity.updateAhtUtilsStatus(aclCategory);
				
				if(category.isSetRoles() && category.getRoles().isSetRole())
				{
					for(Role role : category.getRoles().getRole())
					{
						updateRole.actualAdd(role.getCode());
						iuRole(aclCategory, role);
					}
				}
			}
			catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (AhtUtilsIntegrityException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fSecurity);
		updateRole.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	private void iuRole(C category, Role role) throws AhtUtilsConfigurationException
	{
		R aclRole;
		try
		{
			aclRole = fSecurity.fAhtUtilsByCode(cR,role.getCode());
			
			Map<String,L> langMap = aclRole.getName();
			Map<String,D> descMap = aclRole.getDescription();
			aclRole.setName(null);
			aclRole.setDescription(null);
			
			try{aclRole=(R)fSecurity.updateAhtUtilsStatus(aclRole);}
			catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			
			for(L lang : langMap.values())
			{
				try {fSecurity.rmAhtUtilsEntity(lang);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			}
			
			for(D desc : descMap.values())
			{
				try {fSecurity.rmAhtUtilsEntity(desc);}
				catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
			}
		}
		catch (AhtUtilsNotFoundException e)
		{
			try
			{
				aclRole = cR.newInstance();
				aclRole.setCategory(category);
				aclRole.setCode(role.getCode());
				aclRole = (R)fSecurity.persistAhtUtilsStatus(aclRole);
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
			aclRole=(R)fSecurity.updateAhtUtilsStatus(aclRole);
		}
		catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (AhtUtilsIntegrityException e) {logger.error("",e);}
	}
}