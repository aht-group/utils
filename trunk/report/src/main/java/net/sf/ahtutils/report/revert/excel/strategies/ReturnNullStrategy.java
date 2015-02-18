package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

public class ReturnNullStrategy implements ImportStrategy {

	public Object handleObject(Object object, String parameterClass) {
		return null;
	}

	public void setFacade(UtilsFacade facade) {
		// Not needed here
	}
	
	private Hashtable<String, Object> tempPropertyStore;
	public Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

}
