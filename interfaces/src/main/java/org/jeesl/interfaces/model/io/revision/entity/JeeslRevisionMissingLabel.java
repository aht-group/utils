package org.jeesl.interfaces.model.io.revision.entity;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslRevisionMissingLabel extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,EjbWithId{

	@Override
	long getId();

	@Override
	void setId(long id);

	String getMissingEntity();

	void setMissingEntity(String missingEntity);

	String getMissingCode();

	void setMissingCode(String missingCode);

	String getMissingLocal();

	void setMissingLocal(String missingLocal);

}
