package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslFileStorage<L extends JeeslLang,D extends JeeslDescription,
									SYSTEM extends JeeslIoSsiSystem,
									ENGINE extends JeeslStatus<ENGINE,L,D>>
		extends Serializable,EjbWithId,
			EjbSaveable,EjbRemoveable,
			EjbWithCode,EjbWithPosition,EjbWithLang<L>,EjbWithDescription<D>
				
{	
	ENGINE getEngine();
	void setEngine(ENGINE engines);
	
	String getJson();
	void setJson(String json);
	
	Boolean getKeepRemoved();
	void setKeepRemoved(Boolean keepRemoved);
	
	Long getFileSizeLimit();
	void setFileSizeLimit(Long fileSizeLimit);
}