package org.jeesl.interfaces.model.system;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;

public interface JeeslFeature
		extends EjbWithId,EjbSaveable,
					EjbWithCode,EjbWithPositionVisible
{
	String getName();
	void setName(String name);
	
	String getRemark();
	void setRemark(String remark);
}