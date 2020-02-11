package net.sf.ahtutils.model.interfaces.link;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.date.EjbWithValidUntil;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsLink<S extends JeeslStatus<S,L,D>,
							L extends JeeslLang,
							D extends JeeslDescription>
						extends Serializable,EjbPersistable,EjbRemoveable, EjbWithId,EjbWithCode,EjbWithValidUntil
{
	long getRefId();
	void setRefId(long refId);
	
	S getType();
	void setType(S type);
}