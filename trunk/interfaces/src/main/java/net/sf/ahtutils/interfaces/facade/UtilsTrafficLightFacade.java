package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.interfaces.model.util.UtilsTrafficLight;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsTrafficLightFacade <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends UtilsTrafficLight<L,D,SCOPE>>
					extends UtilsFacade
{		
	List<LIGHT> allOrderedTrafficLights(Class<LIGHT> cLight,SCOPE scope);
}
