package org.jeesl.interfaces.model.with.system.user;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithUser <USER extends JeeslUser<?>> extends EjbWithId
{
	public USER getUser();
	public void setUser(USER user);
}
