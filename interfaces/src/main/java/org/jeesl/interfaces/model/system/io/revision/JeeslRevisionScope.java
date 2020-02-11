package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslRevisionScope<L extends JeeslLang,D extends JeeslDescription,
									RC extends JeeslStatus<RC,L,D>,
									RA extends JeeslRevisionAttribute<L,D,?,?,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>,EjbWithRevisionAttributes<RA>
{		
	RC getCategory();
	void setCategory(RC category);
}