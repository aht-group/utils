package org.jeesl.interfaces.bean.sb;

import java.io.Serializable;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

public interface SbToggleBean extends Serializable
{
	void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException;
}