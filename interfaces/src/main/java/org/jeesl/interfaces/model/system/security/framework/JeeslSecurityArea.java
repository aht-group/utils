package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.attribute.EjbWithVisibleMigration;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityArea<L extends JeeslLang, D extends JeeslDescription,	   
								   V extends JeeslSecurityView<L,D,?,?,?,?>>
			extends Serializable,EjbWithNonUniqueCode,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPosition,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>,EjbWithVisibleMigration
{	
	public enum Attributes{view}
	
	public V getView();
	public void setView(V view);
	
	public boolean isRestricted();
	void setRestricted(boolean restricted);
}