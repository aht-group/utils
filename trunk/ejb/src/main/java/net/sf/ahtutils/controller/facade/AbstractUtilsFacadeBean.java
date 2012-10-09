package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithName;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsFacadeBean
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsFacadeBean.class);
	
	protected UtilsFacadeBean fUtils;
	
	// Persist
	public <T extends EjbWithId> T save(T o) throws UtilsContraintViolationException,UtilsLockingException {return fUtils.save(o);}
	public <T extends Object> T persist(T o) throws UtilsContraintViolationException {return fUtils.persist(o);}
	public <T extends Object> T update(T o) throws UtilsContraintViolationException, UtilsLockingException {return fUtils.update(o);}
	
	// Finder
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException {return fUtils.find(type, id);}
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException {return fUtils.fByCode(type, code);}
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException {return fUtils.fByName(type, name);}
	
	// Ordering
	public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, String by, boolean ascending, String p1Name, I p1,Date from, Date to) {return fUtils.allOrderedParentRecordBetween(cl, by, ascending, p1Name, p1, from, to);}

	// Property Store
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException {return fUtils.valueStringForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException {return fUtils.valueIntForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException {return fUtils.valueLongForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException {return fUtils.valueBooleanForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type,String key, Date defaultValue) throws UtilsNotFoundException {return fUtils.valueDateForKey(type, key, defaultValue);}

}