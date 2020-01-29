package org.jeesl.interfaces.bean.op;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntityBean
{
	void addOpEntity(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
	void rmOpEntity(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
}