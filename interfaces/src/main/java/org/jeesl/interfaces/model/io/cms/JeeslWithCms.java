package org.jeesl.interfaces.model.io.cms;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithCms<CMS extends JeeslIoCms<?,?,?,?,?>>
						extends EjbWithId
{
	public static String attributeCategory = "cms";
	
	CMS getCms();
	void setCms(CMS cms);
}