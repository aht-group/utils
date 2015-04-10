package net.sf.ahtutils.interfaces.facade;

import java.util.List;
import java.util.Set;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

//ahtutils.highlight:interface
public interface UtilsIdFacade
{
	<T extends Object> List<T> all(Class<T> type);
	<T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbWithId> T find(Class<T> type, T t);
	<T extends EjbWithId> List<T> find(Class<T> cl, List<Long> ids);
	<T extends EjbWithId> List<T> find(Class<T> cl, Set<Long> ids);
}
//ahtutils.highlight:interface