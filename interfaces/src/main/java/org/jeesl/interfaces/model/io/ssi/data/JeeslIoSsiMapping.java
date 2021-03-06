package org.jeesl.interfaces.model.io.ssi.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslIoSsiMapping <SYSTEM extends JeeslIoSsiSystem<?,?>,
									ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver
{	
	public enum Attributes {system,entity,json}
	
	public SYSTEM getSystem();
	public void setSystem(SYSTEM system);
	
	public ENTITY getEntity();
	public void setEntity(ENTITY entity);
	
	public ENTITY getJson();
	public void setJson(ENTITY json);
}