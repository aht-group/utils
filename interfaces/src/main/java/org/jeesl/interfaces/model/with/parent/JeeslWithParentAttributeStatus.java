package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.system.status.JeeslWithStatus;

public interface JeeslWithParentAttributeStatus <STATUS extends JeeslStatus<STATUS,?,?>> 
								extends EjbWithParentAttributeResolver,JeeslWithStatus<STATUS>
{


}