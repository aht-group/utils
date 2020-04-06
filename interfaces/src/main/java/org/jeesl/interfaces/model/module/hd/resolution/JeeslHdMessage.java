package org.jeesl.interfaces.model.module.hd.resolution;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.status.JeeslWithScope;

public interface JeeslHdMessage<TICKET extends JeeslHdTicket<?,?,?>,
								SCOPE extends JeeslHdScope<?,?,SCOPE,?>,
								USER extends JeeslSimpleUser>
						extends Serializable,EjbSaveable,
								EjbWithId,
								EjbWithParentAttributeResolver,
								EjbWithRecord,
								JeeslWithScope<SCOPE>
{	
	public enum Attributes{ticket,author,scope}
	
	TICKET getTicket();
	void setTicket(TICKET ticket);
	
	USER getAuthor();
	void setAuthor(USER author);
}