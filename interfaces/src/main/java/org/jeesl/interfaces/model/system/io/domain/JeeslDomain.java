package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslDomain<L extends JeeslLang,
									ENTITY extends JeeslRevisionEntity<L,?,?,?,?,?>
									>
			extends Serializable,EjbWithId,EjbSaveable,
					EjbWithPosition,EjbWithLang<L>//,EjbWithDescription<D>
{
	ENTITY getEntity();
	void setEntity(ENTITY entiy);
}