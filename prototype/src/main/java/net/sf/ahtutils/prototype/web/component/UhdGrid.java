package net.sf.ahtutils.prototype.web.component;

import javax.faces.component.FacesComponent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

@FacesComponent("net.sf.ahtutils.prototype.web.component.UhdGrid")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class UhdGrid extends Grid{
	public UhdGrid()
	{
		super();
		slot=274;
		gutter=20;
	}

}
