package org.jeesl.interfaces.model.system;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFeature
		extends EjbWithId,EjbSaveable,
					EjbWithCode,EjbWithPositionVisible
{
	String getName();
	void setName(String name);
	
	String getRemark();
	void setRemark(String remark);
}