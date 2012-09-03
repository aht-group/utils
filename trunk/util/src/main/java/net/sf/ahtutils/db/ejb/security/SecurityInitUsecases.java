package net.sf.ahtutils.db.ejb.security;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityInitUsecases <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
 								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,USER>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitUsecases.class);
	
	private AhtDbEjbUpdater<U> updateUsecases;
	
	public SecurityInitUsecases(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<USER> cUser,UtilsSecurityFacade fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cUser,fAcl);
	}
	
	public void iuUsecases(Access access) throws UtilsConfigurationException
	{
		updateUsecases = AhtDbEjbUpdater.createFactory(cU);
		updateUsecases.dbEjbs(fSecurity.all(cU));

		iuCategory(access, UtilsSecurityCategory.Type.usecase);
		
		updateUsecases.remove(fSecurity);
		logger.trace("iuRoles finished");
	}
	
	@Override protected void iuChilds(C aclCategory, Category category) throws UtilsConfigurationException
	{
		if(category.isSetUsecases() && category.getUsecases().isSetUsecase())
		{
			for(Usecase usecase : category.getUsecases().getUsecase())
			{
				updateUsecases.actualAdd(usecase.getCode());
				iuUsecase(aclCategory, usecase);
			}
		}
	}
	
	private void iuUsecase(C category, Usecase usecase) throws UtilsConfigurationException
	{
		U ebj;
		try
		{
			ebj = fSecurity.fByCode(cU,usecase.getCode());
			rmLang(ebj);
			rmDescription(ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = cU.newInstance();
				ebj.setCategory(category);
				ebj.setCode(usecase.getCode());
				ebj = fSecurity.persist(ebj);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			ebj.setName(ejbLangFactory.getLangMap(usecase.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(usecase.getDescriptions()));
			ebj.setCategory(category);
			ebj=fSecurity.update(ebj);
			
			ebj = iuListViews(ebj, usecase.getViews());
			ebj = iuListActions(ebj, usecase.getActions());
		}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (UtilsIntegrityException e) {logger.error("",e);}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}