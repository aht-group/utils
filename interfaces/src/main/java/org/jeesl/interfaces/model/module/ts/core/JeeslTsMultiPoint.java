package org.jeesl.interfaces.model.module.ts.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslTsMultiPoint <L extends JeeslLang, D extends JeeslDescription,
									SCOPE extends JeeslTsScope<L,D,?,?,UNIT,?,?>,
									UNIT extends JeeslStatus<UNIT,L,D>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithNonUniqueCode,
				EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{scope}
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	UNIT getUnit();
	void setUnit(UNIT unit);
	
	Boolean getVisible();
	void setVisible(Boolean visible);
}