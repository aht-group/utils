package org.jeesl.interfaces.bean.sb;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface SbSingleBean
{
	void selectSbSingle(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException;
}