package org.jeesl.interfaces.bean.op;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface OpEntityBean
{
	void addOpEntity(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
	void rmOpEntity(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
}