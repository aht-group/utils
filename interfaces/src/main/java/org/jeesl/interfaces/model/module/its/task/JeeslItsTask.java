package org.jeesl.interfaces.model.module.its.task;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;

public interface JeeslItsTask <U extends JeeslSimpleUser>
			extends Serializable,EjbSaveable,EjbRemoveable
{
	public enum Attributes{user}
	
	U getUser();
	void setUser(U user);
}