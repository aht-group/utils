package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithType;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface AhtUtilsFacade
{
	<T extends Object> T persistAhtUtilsStatus(T o) throws UtilsContraintViolationException;
	<T extends Object> T updateAhtUtilsStatus(T o) throws UtilsContraintViolationException;
	<T extends Object> T fAhtUtilsEntity(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbRemoveable> void rmAhtUtilsEntity(T o) throws UtilsContraintViolationException;
	
	<T extends Object> List<T> all(Class<T> type);
	<T extends EjbWithType> List<T> allUtilsForType(Class<T> claszz, String type);
	
	<T extends EjbWithCode> T fAhtUtilsByCode(Class<T> classType, String code) throws UtilsNotFoundException;
	<T extends UtilsStatus<L>,L extends UtilsLang> T fAhtUtilsStatusByCode(Class<T> type, String code) throws UtilsNotFoundException;
	<T extends UtilsStatus<L>,L extends UtilsLang> List<T> allAhtUtilsStatus(Class<T> type);
}