package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.interfaces.facade.JeeslFacade;

public class ReturnNullStrategy implements ImportStrategy {

	public Object handleObject(Object object, String parameterClass, String property) {
		return null;
	}

	public void setFacade(JeeslFacade facade) {
		// Not needed here
	}
	
	private Hashtable<String, Object> tempPropertyStore;
	public Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

}
