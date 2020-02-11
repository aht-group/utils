package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityView<L extends JeeslLang, D extends JeeslDescription,
								   C extends JeeslSecurityCategory<L,D>,
								   R extends JeeslSecurityRole<L,D,C,?,U,A,?>,
								   U extends JeeslSecurityUsecase<L,D,C,R,?,A>,
								   A extends JeeslSecurityAction<L,D,R,?,U,?>
								   >
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithCode,EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<C>,
					JeeslSecurityWithActions<A>
{
	public static final String extractId = "securityViews";
	
	public Boolean getAccessPublic();
	public void setAccessPublic(Boolean accessPublic);
	
	public Boolean getAccessLogin();
	public void setAccessLogin(Boolean accessLogin);
	
	public String getPackageName();
	public void setPackageName(String packageName);
	
	public String getViewPattern();
	public void setViewPattern(String viewPattern);
	
	public String getUrlMapping();
	public void setUrlMapping(String urlMapping);
	
	public String getUrlBase();
	public void setUrlBase(String urlBase);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
	
	public Boolean getRedirect();
	public void setRedirect(Boolean redirect);
}