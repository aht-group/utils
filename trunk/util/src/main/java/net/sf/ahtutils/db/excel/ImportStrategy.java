package net.sf.ahtutils.db.excel;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface ImportStrategy {
	
	public Object 					 handleObject(Object object, String parameterClass);
	public void   					 setFacade(UtilsFacade facade);
	public void   					 setTempPropertyStore(Hashtable<String, Object> tempProperties);
	public Hashtable<String, Object> getTempPropertyStore();
}
