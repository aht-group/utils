package net.sf.ahtutils.db.excel;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface ImportStrategy {
	
	public Object handleObject(Object object, String parameterClass);
	public void   setFacade(UtilsFacade facade);

}
