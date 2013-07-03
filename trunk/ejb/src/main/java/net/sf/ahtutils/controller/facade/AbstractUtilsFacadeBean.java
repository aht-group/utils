package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;
import net.sf.ahtutils.model.interfaces.with.EjbWithTimeline;
import net.sf.ahtutils.model.interfaces.with.EjbWithType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsFacadeBean //implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsFacadeBean.class);
	
	protected UtilsFacadeBean fUtils;
	
	// All
	public <T extends Object> List<T> all(Class<T> type) {return fUtils.all(type);}
	public <T extends EjbWithType> List<T> allForType(Class<T> cl, String type) {return fUtils.allForType(cl, type);}
	
	// Parent
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1){return fUtils.allForParent(type, p1Name, p1);}
	
	// Persist
	public <T extends EjbWithId> T save(T o) throws UtilsContraintViolationException,UtilsLockingException {return fUtils.save(o);}
	public <T extends Object> T persist(T o) throws UtilsContraintViolationException {return fUtils.persist(o);}
	public <T extends Object> T update(T o) throws UtilsContraintViolationException, UtilsLockingException {return fUtils.update(o);}
	
	// Finder
	public <T extends EjbWithId> T find(Class<T> type, T t) {return fUtils.find(type, t);}
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException {return fUtils.find(type, id);}
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException {return fUtils.fByCode(type, code);}
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException {return fUtils.fByName(type, name);}
	
	// Ordering
	public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, String by, boolean ascending, String p1Name, I p1,Date from, Date to) {return fUtils.allOrderedParentRecordBetween(cl, by, ascending, p1Name, p1, from, to);}

	// Parent
	public <T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException {return fUtils.oneForParents(cl, parents);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException {return fUtils.oneForParents(cl, p1Name, p1, p2Name, p2);}
	
	// Property Store
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException {return fUtils.valueStringForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException {return fUtils.valueIntForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException {return fUtils.valueLongForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException {return fUtils.valueBooleanForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type,String key, Date defaultValue) throws UtilsNotFoundException {return fUtils.valueDateForKey(type, key, defaultValue);}

	//Record
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to) {return fUtils.inInterval(clRecord, from, to);}
	public <T extends EjbWithRecord> T fFirst(Class<T> clRecord) {return fUtils.fFirst(clRecord);}
	public <T extends EjbWithRecord> T fLast(Class<T> clRecord) {return fUtils.fLast(clRecord);}
	
	//Timeline
	public <T extends EjbWithTimeline> List<T> between(Class<T> clTimeline, Date from, Date to) {return fUtils.between(clTimeline, from, to);}
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline, Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {return fUtils.between(clTimeline, from, to, lpAnd, lpOr);}
}