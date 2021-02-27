package org.jeesl.interfaces.model.module.lf.target.time;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.status.JeeslWithInterval;

public interface JeeslLfTargetTimeGroup<TTI extends JeeslLfTargetTimeInterval<?,?,TTI,?>>
						extends Serializable,EjbSaveable,
								EjbWithId,
								
								JeeslWithInterval<TTI>
{	
	public enum Attributes{interval}
	
	
}