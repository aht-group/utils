package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface AhtUtilsStatusInterface 
{
	<T extends Object> T persistAhtUtilsStatus(T o) throws AhtUtilsContraintViolationException;
	<T extends Object> T updateAhtUtilsStatus(T o) throws AhtUtilsContraintViolationException;
	<T extends Object> T fAhtUtilsEntity(Class<T> type, long id) throws AhtUtilsNotFoundException;
	void rmAhtUtilsEntity(Object o) throws AhtUtilsContraintViolationException;
	
	<T extends UtilsStatus<L>,L extends UtilsLang> T fStatusByCode(Class<T> type, String code) throws AhtUtilsNotFoundException;
	<T extends UtilsStatus<L>,L extends UtilsLang> List<T> allStatus(Class<T> type);
}