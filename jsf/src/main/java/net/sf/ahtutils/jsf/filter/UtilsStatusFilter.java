package net.sf.ahtutils.jsf.filter;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public class UtilsStatusFilter<L extends JeeslLang, D extends JeeslDescription, S extends JeeslStatus<S,L,D>>		
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