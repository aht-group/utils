package org.jeesl.interfaces.model.module.lf.outdated;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslLogFrameArea<L extends JeeslLang, D extends JeeslDescription,
									IT extends JeeslStatus<IT,L,D>
									>
				extends Serializable,EjbWithId,EjbPersistable,EjbRemoveable
{
	IT getType();
	void setType(IT type);
}