package org.jeesl.interfaces.model.system.io.db;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.status.JeeslWithCategory;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSync<L extends JeeslLang, D extends JeeslDescription,
							STATUS extends JeeslStatus<STATUS,L,D>,
							CATEGORY extends JeeslStatus<CATEGORY,L,D>>
			extends EjbWithId,EjbWithCode,EjbWithRecord,Serializable,EjbPersistable,EjbSaveable,
						JeeslWithStatus<STATUS>,
						JeeslWithCategory<CATEGORY>
{
	public static enum Code{never,pending,success,fail};
}