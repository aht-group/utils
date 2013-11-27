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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDummyFacade implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDummyFacade.class);

	@Override
	public <T> T find(Class<T> type, long id) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T extends EjbWithId> T find(Class<T> type, T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithName> T fByName(Class<T> type, String name)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type,
			String parentName, P parent, long nr) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> all(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithType> List<T> allForType(Class<T> clazz,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId> T save(T o)
			throws UtilsContraintViolationException, UtilsLockingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T persist(T o) throws UtilsContraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T update(T o) throws UtilsContraintViolationException,
			UtilsLockingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbRemoveable> void rm(T o)
			throws UtilsIntegrityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1, String p2Name, I p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(
			Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,
			List<ParentPredicate<OR>> lpOr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type,
			String key, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type,
			String key, Integer defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UtilsProperty> Boolean valueBooleanForKey(
			Class<T> type, String key, Boolean defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type,
			String key, Date defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type,
			String key, Long defaultValue) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type,
			boolean asc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(
			Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,
			List<ParentPredicate<OR>> lpOr, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> allOrdered(Class<T> cl, String by, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(
			Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> T oneForParent(
			Class<T> type, String p1Name, I p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,
			String by, boolean ascending, String p1Name, I p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(
			Class<T> cl, String by, boolean ascending, String p1Name, I p1,
			Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId> T oneForParents(
			Class<T> cl, List<ParentPredicate<P>> parents)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(
			Class<T> cl, String p1Name, I p1, String p2Name, I p2)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord,
			Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T extends EjbWithTimeline> List<T>
		between(Class<T> clTracker, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T>
		between(Class<T> clTimeline, Date from, Date to,List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord> T fFirst(Class<T> clRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithRecord> T fLast(Class<T> clRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithYear, P extends EjbWithId> T fByYear(
			Class<T> type, String p1Name, P p, int year) {
		// TODO Auto-generated method stub
		return null;
	}
}