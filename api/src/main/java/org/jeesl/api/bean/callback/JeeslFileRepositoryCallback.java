package org.jeesl.api.bean.callback;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslFileRepositoryCallback
{	
	void callbackFrContainerSaved(EjbWithId id) throws JeeslConstraintViolationException, JeeslLockingException;
	void callbackFrMetaSelected();
}