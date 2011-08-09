package net.sf.ahtutils.model.interfaces;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface EjbWithLang<L extends UtilsLang>
{	
	public Map<String, L> getName();
	public void setName(Map<String, L> name);
}