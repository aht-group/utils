package org.jeesl.interfaces.model.module.lf;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLfConfiguration<LF extends JeeslLfLogframe,
									IT extends JeeslLfIndicatorType<?,?,?,?,?>>
						extends EjbWithId,EjbSaveable,EjbRemoveable
								
{
	public enum Attributes{interval}
	
	LF getLogframe();
	void setLogframe(LF logframe);
	
	IT getType();
	void setType(IT type);
	
	
	Boolean isAllowTargetValues();
	void setAllowTargetValues(Boolean allowTargetValues);
	
}