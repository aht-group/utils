package org.jeesl.interfaces.model.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslFileReplicationInfo<REP extends JeeslFileReplication<?,?,?,?>>
		extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable
{
	public enum Attributes{storageSrc}
	
}