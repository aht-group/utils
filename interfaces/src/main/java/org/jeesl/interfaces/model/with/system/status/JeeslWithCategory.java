package org.jeesl.interfaces.model.with.system.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithCategory<CATEGORY extends JeeslStatus<CATEGORY,?,?>> extends EjbWithId
{
	public static String attributeCategory = "category";
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}