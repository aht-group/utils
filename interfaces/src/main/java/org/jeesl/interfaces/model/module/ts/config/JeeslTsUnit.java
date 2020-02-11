package org.jeesl.interfaces.model.module.ts.config;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusWithSymbol;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;

public interface JeeslTsUnit <L extends JeeslLang, D extends JeeslDescription,
								S extends JeeslStatus<S,L,D>, 
								G extends JeeslGraphic<L,D,?,?,?>>
					extends Serializable,EjbPersistable,
								EjbWithCode,JeeslStatusFixedCode,JeeslStatusWithSymbol,
//								JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
								JeeslStatus<S,L,D>
{	
	public enum Code{event}
}