package net.sf.ahtutils.model.interfaces;

public interface EjbWithCode
{	
	public void setId(long id);
	public long getId();
	
	public String getCode();
	public void setCode(String code);
}