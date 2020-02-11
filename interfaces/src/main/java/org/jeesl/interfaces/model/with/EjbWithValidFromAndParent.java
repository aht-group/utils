package org.jeesl.interfaces.model.with;

import org.jeesl.interfaces.model.with.date.EjbWithValidFrom;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithValidFromAndParent extends EjbWithId,EjbWithValidFrom,EjbWithParentAttributeResolver
{
	
}