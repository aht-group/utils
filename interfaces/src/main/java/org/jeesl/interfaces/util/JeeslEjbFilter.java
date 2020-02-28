package org.jeesl.interfaces.util;

import java.util.Map;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslEjbFilter <T extends EjbWithId>
{
	boolean matches(Map<String,Object> filters, T ejb);
}