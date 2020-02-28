package org.jeesl.interfaces.model.system.io.ssi.docker;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslIoSsiDocker <SYSTEM extends JeeslIoSsiSystem>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{
	public SYSTEM getSystem();
	public void setSystem(SYSTEM system);
	
}