package net.sf.ahtutils.interfaces.facade;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsIdFacade
{
	<T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbWithId> T find(Class<T> type, T t);
}
