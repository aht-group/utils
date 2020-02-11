package org.jeesl.api.facade.system.graphic;

import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;

public interface JeeslTrafficLightFacade <L extends JeeslLang,D extends JeeslDescription,
											LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
											SCOPE extends JeeslStatus<SCOPE,L,D>>
					extends JeeslFacade
{	
	public List<LIGHT> allOrderedTrafficLights();
	List<LIGHT> allOrderedTrafficLights(SCOPE scope);
}