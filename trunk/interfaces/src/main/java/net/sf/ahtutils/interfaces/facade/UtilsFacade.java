package net.sf.ahtutils.interfaces.facade;

import java.util.Date;
import java.util.List;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;
import net.sf.ahtutils.model.interfaces.with.EjbWithTimeline;
import net.sf.ahtutils.model.interfaces.with.EjbWithType;

public interface UtilsFacade
{
	<T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbWithId> T find(Class<T> type, T t);
	<T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException;
	<T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException;
	<T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, int nr) throws UtilsNotFoundException;
	
	<T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Date valueDateForKey(Class<T> type, String key, Date defaultValue) throws UtilsNotFoundException;
	
	<T extends Object> List<T> all(Class<T> type);
	
	<T extends EjbWithType> List<T> allForType(Class<T> clazz, String type);
	
	// ORDERING
	<T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending);
	<T extends Object,I extends EjbWithId> List<T> allOrderedParent(Class<T> cl, String by, boolean ascending,String p1Name, I p1);
	<T extends EjbWithRecord,I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, String by, boolean ascending,String p1Name, I p1,Date from, Date to);
	<T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type);
	<T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> type);
	<T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending);
	<T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr,boolean ascending);
	
//	<T extends EjbMergeable> T merge(T o) throws UtilsContraintViolationException, UtilsLockingException;
	<T extends Object> T persist(T o) throws UtilsContraintViolationException;
	<T extends Object> T update(T o) throws UtilsContraintViolationException,UtilsLockingException;
	<T extends EjbWithId> T save(T o) throws UtilsContraintViolationException,UtilsLockingException;
	
	<T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException;
	
	//Parent
	<T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1);
	<T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException;
	<T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException;
	<T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException;
	<T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2);
	<T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
	<T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
	<T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2);
	
	//Record
	<T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to);
	<T extends EjbWithRecord> T fFirst(Class<T> clRecord);
	<T extends EjbWithRecord> T fLast(Class<T> clRecord);
	
	//Timeline
	<T extends EjbWithTimeline> List<T> between(Class<T> clTracker, Date from, Date to);
	<T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline,Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
}
