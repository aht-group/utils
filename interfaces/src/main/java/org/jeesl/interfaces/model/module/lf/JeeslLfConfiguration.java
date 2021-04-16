package org.jeesl.interfaces.model.module.lf;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLfConfiguration<LF extends JeeslLfLogframe,
									LFT extends JeeslLfIndicatorType<?,?,?,?,?>>
						extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver
{
	public enum Attributes{interval,logframe}

	LF getLogframe();
	void setLogframe(LF logframe);

	LFT getType();
	void setType(JeeslLfIndicatorType<?,?,?,?,?> type);

	boolean isAllowTargetValues();
	void setAllowTargetValues(boolean allowTargetValues);

}