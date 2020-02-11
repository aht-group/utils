package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityTemplate<L extends JeeslLang, D extends JeeslDescription,
								   C extends JeeslSecurityCategory<L,D>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithCode,EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<C>
{
	public boolean getDocumentation();
	public void setDocumentation(boolean documentation);
}