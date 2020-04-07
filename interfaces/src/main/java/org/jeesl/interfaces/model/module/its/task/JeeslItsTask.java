package org.jeesl.interfaces.model.module.its.task;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslItsTask <I extends JeeslItsIssue<?,I>,
								TT extends JeeslItsTaskType<?,?,TT,?>,
								U extends JeeslSimpleUser>
			extends Serializable,EjbSaveable,EjbRemoveable,
					JeeslWithType<TT>
{
	public enum Attributes{issue,user}
	
	I getIssue();
	void setIssue(I issue);
	
	U getUser();
	void setUser(U user);
}