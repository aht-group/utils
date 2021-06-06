package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithInterval<INTERVAL extends JeeslStatus<?,?,INTERVAL>> extends EjbWithId
{
	INTERVAL getInterval();
	void setInterval(INTERVAL interval);
}