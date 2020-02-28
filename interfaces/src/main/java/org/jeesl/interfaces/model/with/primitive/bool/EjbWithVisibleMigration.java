package org.jeesl.interfaces.model.with.primitive.bool;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithVisibleMigration extends EjbWithId
{
	public Boolean getVisible();
	public void setVisible(Boolean visible);
}