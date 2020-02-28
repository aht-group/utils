package org.jeesl.interfaces.model.system.io.revision.core;

import org.hibernate.envers.RevisionType;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevision;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslRevisionContainer <REV extends JeeslRevision,
										T extends EjbWithId,
										USER extends JeeslUser<?>>
{					
	REV getInfo();
	
	RevisionType getType();
	
	USER getUser();
	void setUser(USER user);
	
	T getEntity();
}