package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityAction<L extends JeeslLang, D extends JeeslDescription,
								   R extends JeeslSecurityRole<L,D,?,V,U,?,?>,
								   V extends JeeslSecurityView<L,D,?,R,U,?>,
								   U extends JeeslSecurityUsecase<L,D,?,R,V,?>,
								   AT extends JeeslSecurityTemplate<L,D,?>>
			extends Serializable,EjbWithCode,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{view}
	
	public String toCode();
	public Map<String,L> toName();
	
	public V getView();
	public void setView(V view);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
	
	public AT getTemplate();
	public void setTemplate(AT template);
}