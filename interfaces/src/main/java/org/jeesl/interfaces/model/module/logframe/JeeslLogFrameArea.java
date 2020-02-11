package org.jeesl.interfaces.model.module.logframe;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslLogFrameArea<L extends JeeslLang, D extends JeeslDescription,
									AREA extends JeeslLogFrameArea<L,D,AREA,IT>,
									IT extends JeeslStatus<IT,L,D>
									>		
{
	IT getType();
	void setType(IT type);
}