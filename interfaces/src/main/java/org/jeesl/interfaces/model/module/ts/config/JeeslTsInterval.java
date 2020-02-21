package org.jeesl.interfaces.model.module.ts.config;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

public interface JeeslTsInterval <L extends JeeslLang, D extends JeeslDescription,
								S extends JeeslStatus<S,L,D>,
								G extends JeeslGraphic<L,D,?,?,?>>
							extends Serializable,EjbSaveable,
										EjbWithCode,JeeslStatusFixedCode,
		//								JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
										JeeslStatus<S,L,D>
{	
	public enum Code{irregular,minute,minute10,day,daily,monthly,weekly,quarterly,yearly}
}