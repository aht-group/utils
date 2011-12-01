package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface AhtUtilsFacade
{
	<T extends Object> T persistAhtUtilsStatus(T o) throws AhtUtilsContraintViolationException;
	<T extends Object> T updateAhtUtilsStatus(T o) throws AhtUtilsContraintViolationException;
	<T extends Object> T fAhtUtilsEntity(Class<T> type, long id) throws AhtUtilsNotFoundException;
	<T extends EjbRemoveable> void rmAhtUtilsEntity(T o) throws AhtUtilsContraintViolationException;
	
	<T extends EjbWithCode> T fAhtUtilsByCode(Class<T> type, String code) throws AhtUtilsNotFoundException;
	<T extends UtilsStatus<L>,L extends UtilsLang> T fAhtUtilsStatusByCode(Class<T> type, String code) throws AhtUtilsNotFoundException;
	<T extends UtilsStatus<L>,L extends UtilsLang> List<T> allAhtUtilsStatus(Class<T> type);
}