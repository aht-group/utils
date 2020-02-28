package org.jeesl.interfaces.model.util;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface UtilsRankedResult <T extends EjbWithId>
{	
	double getRanking();
	void setRanking(double ranking);
	
	T getEntity();
	void setEntity(T entity);
}