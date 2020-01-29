package org.jeesl.interfaces.bean.sb;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

public interface SbToggleBean
{
	void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException;
}