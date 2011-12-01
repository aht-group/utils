package net.sf.ahtutils.controller.factory.utils.acl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.interfaces.AhtAclFacade;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.idm.UtilsIdentity;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdentityFactory  <I extends UtilsIdentity<L,D,CU,UC,U>,
									L extends UtilsLang,
									D extends UtilsDescription,
									CU extends UtilsAclCategoryUsecase<L,D,CU,UC>,
									CR extends UtilsAclCategoryRole<L,D,CU,CR,UC,R>,
									R extends UtilsAclRole<L,D,CU,CR,UC,R>,
									UC extends UtilsAclUsecase<L,D,CU,UC>,
									U extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(UtilsIdentityFactory.class);

	final Class<I> clIdentity;
	final Class<L> clLang;
	final Class<D> clDescription;
	final Class<CU> clCategory;
	final Class<CR> clCategoryRole;
	final Class<R> clRole;
	final Class<UC> clUsecase;
	final Class<U> clUser;

	public UtilsIdentityFactory(final Class<I> clIdentity,
								final Class<L> clLang,
								final Class<D> clDescription,
								final Class<CU> clCategory,
								final Class<CR> clCategoryRole,
								final Class<R> clRole,
								final Class<UC> clUsecase,
								final Class<U> clUser)
	{
		this.clIdentity=clIdentity;
		this.clLang=clLang;
		this.clDescription=clDescription;
		this.clCategory=clCategory;
		this.clCategoryRole=clCategoryRole;
		this.clRole=clRole;
		this.clUsecase=clUsecase;
		this.clUser = clUser;
	} 

	public static <I extends UtilsIdentity<L,D,CU,UC,U>,
					L extends UtilsLang,
					D extends UtilsDescription,
					CU extends UtilsAclCategoryUsecase<L,D,CU,UC>,
					CR extends UtilsAclCategoryRole<L,D,CU,CR,UC,R>,
					R extends UtilsAclRole<L,D,CU,CR,UC,R>,
					UC extends UtilsAclUsecase<L,D,CU,UC>,
					U extends EjbWithId> UtilsIdentityFactory<I,L,D,CU,CR,R,UC,U>
	factory(final Class<I> clIdentity,
					final Class<L> clLang,
					final Class<D> clDescription,
					final Class<CU> clCategory,
					final Class<CR> clCategoryRole,
					final Class<R> clRole,
					final Class<UC> clUsecase,
					final Class<U> clUser)
	{
		return new UtilsIdentityFactory<I,L,D,CU,CR,R,UC,U>(clIdentity,clLang,clDescription,clCategory,clCategoryRole,clRole,clUsecase,clUser);
	}

	public I create(AhtAclFacade fAcl, U user) throws InstantiationException, IllegalAccessException
	{
		return create(fAcl, user, new ArrayList<R>());
	}
	public I create(AhtAclFacade fAcl, U user, List<R> roles) throws InstantiationException, IllegalAccessException
	{
		I identity = clIdentity.newInstance();
		identity.setUser(user);
		
		
		for(String code : fAcl.findUsecaseCodesForRoles(roles))
		{
			try {identity.allowUsecase(fAcl.fAhtUtilsByCode(clUsecase, code));}
			catch (AhtUtilsNotFoundException e) {logger.error("This should not happen ...",e);}
		}
		
		return identity;
	}
}