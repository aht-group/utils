package org.jeesl.interfaces.model.system.locale;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLang extends EjbWithId,EjbRemoveable,EjbSaveable
{	
	public static String attributeLang = "lang";
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String lang);
}