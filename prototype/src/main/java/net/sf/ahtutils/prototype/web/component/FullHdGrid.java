package net.sf.ahtutils.prototype.web.component;

import javax.faces.component.FacesComponent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

@FacesComponent("net.sf.ahtutils.prototype.web.component.FullHdGrid")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class FullHdGrid extends Grid{
	public FullHdGrid()
	{
		super();
		slot=140;
		gutter=10;
	}

}
