package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.crud.EjbCrudWithParent;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaSchedule<QA extends UtilsQualityAssurarance<?,?,?>,
								QASS extends UtilsQaScheduleSlot<?,?>>
			extends Serializable,EjbCrudWithParent,EjbPersistable,EjbRemoveable,EjbWithId,EjbWithCode,EjbWithName
{
	QA getQa();
	void setQa(QA qa);
	
	List<QASS> getSlots();
	void setSlots(List<QASS> slots);
}