package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithCategory<CATEGORY extends JeeslStatus<CATEGORY,?,?>> extends EjbWithId
{
	public static String attributeCategory = "category";
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}