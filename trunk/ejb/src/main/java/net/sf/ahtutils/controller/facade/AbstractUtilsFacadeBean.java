package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.date.EjbWithTimeline;
import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;
import net.sf.ahtutils.model.interfaces.with.EjbWithType;
import net.sf.ahtutils.model.interfaces.with.EjbWithValidFrom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsFacadeBean implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsFacadeBean.class);
	
	protected UtilsFacadeBean fUtils;
	
	// Persist
	public <T extends EjbWithId> T save(T o) throws UtilsContraintViolationException,UtilsLockingException {return fUtils.save(o);}
	public <T extends Object> T persist(T o) throws UtilsContraintViolationException {return fUtils.persist(o);}
	public <T extends Object> T update(T o) throws UtilsContraintViolationException, UtilsLockingException {return fUtils.update(o);}
	
	// Finder
	public <T extends EjbWithId> T find(Class<T> type, T t) {return fUtils.find(type, t);}
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException {return fUtils.find(type, id);}
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException {return fUtils.fByCode(type, code);}
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException {return fUtils.fByName(type, name);}
	@Override public <T extends EjbWithEmail> T fByEmail(Class<T> clazz, String email) throws UtilsNotFoundException {return fUtils.fByEmail(clazz, email);}
	
	// Remove
	public <T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException {rmProtected(o);}
	protected <T extends Object> void rmProtected(T o) throws UtilsIntegrityException {fUtils.rmProtected(o);}
	
	// All
	public <T extends Object> List<T> all(Class<T> type) {return fUtils.all(type);}
	public <T extends EjbWithType> List<T> allForType(Class<T> cl, String type) {return fUtils.allForType(cl, type);}
	
	// Ordering
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type) {return fUtils.allOrderedPosition(type);}
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> cl) {return fUtils.allOrderedPositionVisible(cl);}
	@Override public <T extends EjbWithPositionVisible, P extends EjbWithId> List<T> allOrderedPositionVisibleParent(Class<T> cl, P parent) {return fUtils.allOrderedPositionVisibleParent(cl,parent);}
	public <T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending) {return fUtils.allOrdered(cl, by, ascending);}
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending) {return fUtils.allOrderedRecord(type,ascending);}
	public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, String by, boolean ascending, String p1Name, I p1,Date from, Date to) {return fUtils.allOrderedParentRecordBetween(cl, by, ascending, p1Name, p1, from, to);}
	public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,String by, boolean ascending, String p1Name, I p1) {return fUtils.allOrderedParent(cl, by, ascending, p1Name, p1);}
	
	// Parent
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1Object){return fUtils.allForParent(type, p1Name, p1Object);}
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2){return fUtils.allForParent(type, p1Name, p1, p2Name, p2);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException {return fUtils.oneForParent(cl, p1Name, p1);}
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, long nr) throws UtilsNotFoundException {return fUtils.fByNr(type, parentName, parent, nr);}
	public <T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException {return fUtils.oneForParents(cl, parents);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException {return fUtils.oneForParents(cl, p1Name, p1, p2Name, p2);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3) throws UtilsNotFoundException {return fUtils.oneForParents(cl,p1Name,p1,p2Name,p2,p3Name,p3);}
	@Override public <T extends EjbWithId, P extends EjbWithId> List<T> allForOrParents(Class<T> cl, List<ParentPredicate<P>> parents) {return fUtils.allForOrParents(cl, parents);}
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,List<ParentPredicate<OR>> lpOr, boolean ascending) {return fUtils.allOrderedForParents(queryClass, lpAnd, lpOr, ascending);}
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {return fUtils.fForAndOrParents(queryClass, lpAnd, lpOr);}
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr){return fUtils.fForAndOrGrandParents(queryClass, parentClass, parentName, lpAnd, lpOr);}
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2){return fUtils.fGrandParents(queryClass, parentClass, parentName, lpOr1, lpOr2);}
	
	// Property Store
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException {return fUtils.valueStringForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException {return fUtils.valueIntForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException {return fUtils.valueLongForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException {return fUtils.valueBooleanForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type,String key, Date defaultValue) throws UtilsNotFoundException {return fUtils.valueDateForKey(type, key, defaultValue);}

	// ValidFrom
	public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException {return fUtils.fFirstValidFrom(type, parentName, id, validFrom);}
	
	//Record
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to) {return fUtils.inInterval(clRecord, from, to);}
	public <T extends EjbWithRecord> T fFirst(Class<T> clRecord) {return fUtils.fFirst(clRecord);}
	public <T extends EjbWithRecord> T fLast(Class<T> clRecord) {return fUtils.fLast(clRecord);}
	
	//Timeline
	public <T extends EjbWithTimeline> List<T> between(Class<T> clTimeline, Date from, Date to) {return fUtils.between(clTimeline, from, to);}
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline, Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {return fUtils.between(clTimeline, from, to, lpAnd, lpOr);}
	
	//Year
	public <T extends EjbWithYear, P extends EjbWithId> T fByYear(Class<T> type, String p1Name, P p, int year) throws UtilsNotFoundException {return fUtils.fByYear(type, p1Name, p, year);}
	
	
}