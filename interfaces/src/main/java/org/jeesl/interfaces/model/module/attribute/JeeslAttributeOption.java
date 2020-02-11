package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeOption<L extends JeeslLang, D extends JeeslDescription,CRITERIA extends JeeslAttributeCriteria<L,D,?,?>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithNonUniqueCode,EjbWithPosition,EjbWithPositionParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{criteria}
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);
}