package org.jeesl.interfaces.model.module.lf.target.time;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLfTargetTimeElement<TTG extends JeeslLfTargetTimeGroup<?>>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithRecord,EjbWithParentAttributeResolver
{	
	public enum Attributes{group}
	
	TTG getGroup();
	void setGroup(TTG group);
}