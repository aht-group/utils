package org.jeesl.interfaces.model.module.lf.indicator;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslLfIndicator<LF extends JeeslLfLogframe,
									IL extends JeeslLfIndicatorLevel<?,?,?,?,?>,
									IT extends JeeslLfIndicatorType<?,?,?,?,?>>
						extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithName
								
{
	public enum Attributes{interval}
	
	LF getLogframe();
	void setLogframe(LF logframe);
	
	IL getLevel();
	void setLevel(IL level);
	
	IT getType();
	void setType(IT type);
}