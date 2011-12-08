package net.sf.ahtutils.db.ejb.security;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
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
		extends AbstractSecurityInit<L,D,C,R,V,U,A>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitRoles.class);
	
	private AhtDbEjbUpdater<R> updateRole;
	
	public SecurityInitRoles(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,AhtSecurityFacade fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public void iuRoles(Access access) throws AhtUtilsConfigurationException
	{
		updateRole = AhtDbEjbUpdater.createFactory(cR);
		updateRole.dbEjbs(fSecurity.all(cR));

		iuCategory(access, UtilsSecurityCategory.Type.role);
		
		updateRole.remove(fSecurity);
		logger.trace("iuRoles finished");
	}
	
	@Override protected void iuChilds(C aclCategory, Category category) throws AhtUtilsConfigurationException
	{
		if(category.isSetRoles() && category.getRoles().isSetRole())
		{
			for(Role role : category.getRoles().getRole())
			{
				updateRole.actualAdd(role.getCode());
				iuRole(aclCategory, role);
			}
		}
	}
	
	private void iuRole(C category, Role role) throws AhtUtilsConfigurationException
	{
		R aclRole;
		try
		{
			aclRole = fSecurity.fAhtUtilsByCode(cR,role.getCode());
			rmLang(aclRole);
			rmDescription(aclRole);
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
			aclRole.setDescription(ejbDescriptionFactory.create(role.getDescriptions()));
			aclRole.setCategory(category);
			aclRole=(R)fSecurity.updateAhtUtilsStatus(aclRole);
		}
		catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (AhtUtilsIntegrityException e) {logger.error("",e);}
	}
}