package org.jeesl.interfaces.model.system.security.util;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.date.EjbWithValidUntil;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithUser;

public interface JeeslRememberMe <USER extends JeeslUser<?>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithValidUntil,
				EjbWithUser<USER>
{	

}