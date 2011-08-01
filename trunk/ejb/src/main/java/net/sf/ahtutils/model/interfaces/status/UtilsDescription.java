package net.sf.ahtutils.model.interfaces.status;

public interface UtilsDescription extends UtilsRemoveable
{
	long getId();
	void setId(long id);
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
}