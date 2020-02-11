package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

public interface JeeslDmsViewType <S extends JeeslStatus<S,L,D>,
										L extends JeeslLang, D extends JeeslDescription,
										G extends JeeslGraphic<L,D,?,?,?>>
		extends Serializable,EjbPersistable,JeeslOptionRestDownload,JeeslStatusFixedCode,EjbWithCodeGraphic<G>
{
	public enum Code{tree}
}