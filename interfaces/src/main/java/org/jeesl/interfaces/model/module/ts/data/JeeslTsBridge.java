package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.with.number.EjbWithRefId;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsBridge <EC extends JeeslTsEntityClass<?,?,?>>
					extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbWithRefId,EjbWithParentAttributeResolver
{
	enum Attributes{entityClass,refId}
	
	EC getEntityClass();
	void setEntityClass(EC entityClass);
}