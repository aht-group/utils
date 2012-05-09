package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithName;
import net.sf.ahtutils.model.interfaces.EjbWithNr;
import net.sf.ahtutils.model.interfaces.EjbWithType;
import net.sf.ahtutils.model.interfaces.with.EjbWithStatus;

public interface UtilsFacade
{
	<T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException;
	<T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException;
	<T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, int nr) throws UtilsNotFoundException;
	
	<T extends Object> List<T> all(Class<T> type);
	<T extends EjbWithType> List<T> allForType(Class<T> clazz, String type);
	
	<T extends Object> T persist(T o) throws UtilsContraintViolationException;
	<T extends Object> T update(T o) throws UtilsContraintViolationException,UtilsLockingException;
	
	<T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException;
	
	<T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2);
	<T extends EjbWithStatus, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<OR>> lpOr, List<ParentPredicate<AND>> lpAnd);
}
