package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.date.EjbWithValidUntil;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslJobCache<TEMPLATE extends JeeslJobTemplate<?,?,?,?,?,?>,
								CONTAINER extends JeeslFileContainer<?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithNonUniqueCode,EjbWithRecord,EjbWithValidUntil
{
	public static enum Attributes{template,code};
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	byte[] getData();
	void setData(byte[] data);
}