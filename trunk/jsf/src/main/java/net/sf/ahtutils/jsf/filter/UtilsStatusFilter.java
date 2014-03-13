package net.sf.ahtutils.jsf.filter;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public class UtilsStatusFilter<L extends UtilsLang, D extends UtilsDescription, S extends UtilsStatus<S,L,D>>		
{		
	private S value;
	private boolean active;
	
	public UtilsStatusFilter(S value, boolean active)
	{
		this.active=active;
		this.value=value;
	}
	
	public S getValue() {return value;}
	public void setValue(S value) {this.value = value;}

	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}
}