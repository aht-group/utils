package net.sf.ahtutils.model.interfaces.status;

import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;

public interface UtilsDescription extends EjbRemoveable
{
	long getId();
	void setId(long id);
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
}