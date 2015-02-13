package net.sf.ahtutils.db.excel.strategies;

import net.sf.ahtutils.db.excel.ImportStrategy;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class ReturnNullStrategy implements ImportStrategy {

	@Override
	public Object handleObject(Object object, String parameterClass) {
		return null;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		// Not needed here
	}

}
