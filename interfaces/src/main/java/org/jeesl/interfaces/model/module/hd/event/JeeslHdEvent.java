package org.jeesl.interfaces.model.module.hd.event;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.status.JeeslWithCategory;
import org.jeesl.interfaces.model.with.system.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslHdEvent<TICKET extends JeeslHdTicket<?,?>,
								CAT extends JeeslHdTicketCategory<?,?,?,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,?,STATUS,?>,
								TYPE extends JeeslHdEventType<?,?,TYPE,?>,
								USER extends JeeslSimpleUser>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithRecord,
								JeeslWithType<TYPE>,
								JeeslWithCategory<CAT>,JeeslWithStatus<STATUS>
{	
	public enum Attributes{xx}
	
	TICKET getTicket();
	void setTicket(TICKET ticket);
	
	USER getReporter();
	void setReporter(USER reporter);
	
	USER getSupporter();
	void setSupporter(USER user);
}