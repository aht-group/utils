package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.with.primitive.date.EjbWithValidFrom;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithValidFromAndParent extends EjbWithId,EjbWithValidFrom,EjbWithParentAttributeResolver
{
	
}