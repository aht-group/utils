package org.jeesl.interfaces.model.with.primitive.position;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithPositionMigration extends EjbWithId
{
	public Integer getPosition();
	public void setPosition(Integer position);
}