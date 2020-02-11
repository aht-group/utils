package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDomainPath<L extends JeeslLang, D extends JeeslDescription,
										QUERY extends JeeslDomainQuery<L,D,?,?>,
										ENTITY extends JeeslRevisionEntity<L,D,?,?,?,?>,
										ATTRIBUTE extends JeeslRevisionAttribute<L,D,?,?,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithPositionParent
{
	public enum Attributes{query}
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
	ENTITY getEntity();
	void setEntity(ENTITY entity);
	
	ATTRIBUTE getAttribute();
	void setAttribute(ATTRIBUTE attribute);
}