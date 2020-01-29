package org.jeesl.interfaces.bean.th;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

public interface ThMultiFilterBean
{
	void filtered(ThMultiFilter filter) throws JeeslLockingException, JeeslConstraintViolationException;
}