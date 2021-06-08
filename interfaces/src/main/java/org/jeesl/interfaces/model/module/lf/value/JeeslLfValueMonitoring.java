package org.jeesl.interfaces.model.module.lf.value;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLfValueMonitoring<LFI extends JeeslLfIndicator<?,?,?,?,?,TTG,?>,
						TTG extends JeeslLfTimeGroup<?,?>,
						TTE extends JeeslLfTimeElement<?,?>>
				extends  Serializable,EjbSaveable,EjbRemoveable,EjbWithId,EjbWithParentAttributeResolver
{
	public enum Attributes{indicator}
	
	LFI getIndicator();
	void setIndicator(LFI indicator);
	
}
