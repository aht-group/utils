package org.jeesl.interfaces.util;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslEjbFilter <T extends EjbWithId>
{
	boolean matches(Map<String,Object> filters, T ejb);
}