package org.jeesl.interfaces.model.system.io.ssi.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiCredential <SYSTEM extends JeeslIoSsiSystem>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode
{	
	public enum Attributes {entity,json}
	
	public SYSTEM getSystem();
	public void setSystem(SYSTEM system);
}