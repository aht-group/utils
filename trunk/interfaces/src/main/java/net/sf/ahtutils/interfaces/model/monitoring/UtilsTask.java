package net.sf.ahtutils.interfaces.model.monitoring;

import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsTask<T extends UtilsTask<T>> extends EjbWithId,EjbWithCode,EjbWithName
{	
//	public ErpTask getParentTask() {return parentTask;}
//	public void setParentTask(ErpTask parentTask) {this.parentTask = parentTask;}
}
