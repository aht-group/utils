package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public interface JeeslLogframeFacade < L extends JeeslLang, D extends JeeslDescription,
									LF extends JeeslLfLogframe<?,?,?,?>,
									//LFL extends JeeslLfLevel<?,?,LFL,?>,
									//LFT extends JeeslLfType<?,?,LFT,?>,
									TTG extends JeeslLfTargetTimeGroup<?,?>,
									//TTI extends JeeslLfTargetTimeInterval<?,?,TTI,?>,
									TTE extends JeeslLfTargetTimeElement<?,TTG>
									>
			extends JeeslFacade
{

}