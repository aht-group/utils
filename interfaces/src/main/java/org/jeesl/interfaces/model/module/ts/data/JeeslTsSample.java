package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsSample 
		extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithRecord
{
	enum Attributes{record}
}