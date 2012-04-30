package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsSecurityFacade extends UtilsFacade
{	
	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,R extends UtilsSecurityRole<L,D,C,R,V,U,A>,V extends UtilsSecurityView<L,D,C,R,V,U,A>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,A extends UtilsSecurityAction<L,D,C,R,V,U,A>,US extends UtilsUser<L,D,C,R,V,U,A>>
		List<V> allViewsForUser(Class<US> clUser, US user);
	
	<WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A>, L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,R extends UtilsSecurityRole<L,D,C,R,V,U,A>,V extends UtilsSecurityView<L,D,C,R,V,U,A>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,A extends UtilsSecurityAction<L,D,C,R,V,U,A>,US extends UtilsUser<L,D,C,R,V,U,A>>
		List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String catCode) throws UtilsNotFoundException;
	
	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,R extends UtilsSecurityRole<L,D,C,R,V,U,A>,V extends UtilsSecurityView<L,D,C,R,V,U,A>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,A extends UtilsSecurityAction<L,D,C,R,V,U,A>,S extends UtilsStaff<L,D,C,R,V,U,A,P,E>,P extends EjbWithId,E extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, P pool);
}
