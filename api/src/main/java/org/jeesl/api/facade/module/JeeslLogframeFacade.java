package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public interface JeeslLogframeFacade < L extends JeeslLang, D extends JeeslDescription,
									LF extends JeeslLfLogframe<?,?,?,?,?,?>,
									LFI extends JeeslLfIndicator<LF,?,?,TTG,LFM>,
									TTG extends JeeslLfTargetTimeGroup<?,?>,
									TTE extends JeeslLfTargetTimeElement<?,TTG>,
									LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>
									>
			extends JeeslFacade
{

}