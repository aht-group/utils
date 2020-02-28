package org.jeesl.interfaces.bean.sb;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface SbSingleBean
{
	void selectSbSingle(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException;
}