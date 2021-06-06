package org.jeesl.interfaces.model.system.security.user;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslRegistration <L extends JeeslLang, D extends JeeslDescription,
							USER extends JeeslUser<?>,
							REGSTATUS extends JeeslStatus<L,D,REGSTATUS>>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	USER getUser();
	void setUser(USER user);
	
	REGSTATUS getStatus();
	void setStatus(REGSTATUS status);
}