package org.jeesl.interfaces.bean.th;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface ThMultiFilterBean
{
	void filtered(ThMultiFilter filter) throws UtilsLockingException, UtilsConstraintViolationException;
}