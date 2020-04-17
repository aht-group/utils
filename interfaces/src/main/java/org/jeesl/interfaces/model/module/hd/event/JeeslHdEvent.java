package org.jeesl.interfaces.model.module.hd.event;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.status.JeeslWithCategory;
import org.jeesl.interfaces.model.with.system.status.JeeslWithLevel;
import org.jeesl.interfaces.model.with.system.status.JeeslWithStatus;

public interface JeeslHdEvent<TICKET extends JeeslHdTicket<?,?,?,?>,
								CAT extends JeeslHdTicketCategory<?,?,?,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,?,STATUS,?>,
								TYPE extends JeeslHdEventType<?,?,TYPE,?>,
								LEVEL extends JeeslHdLevel<?,?,?,LEVEL,?>,
								PRIORITY extends JeeslHdPriority<?,?,?,PRIORITY,?>,
								USER extends JeeslSimpleUser>
						extends Serializable,EjbSaveable,
								EjbWithId,
								EjbWithParentAttributeResolver,
								EjbWithRecord,
								JeeslWithCategory<CAT>,JeeslWithStatus<STATUS>,JeeslWithLevel<LEVEL>
{	
	public enum Attributes{ticket,reporter,status}
	
	TICKET getTicket();
	void setTicket(TICKET ticket);
	
	USER getInitiator();
	void setInitiator(USER initiator);
	
	USER getReporter();
	void setReporter(USER reporter);
	
	USER getSupporter();
	void setSupporter(USER user);
	
	List<TYPE> getTypes();
	void setTypes(List<TYPE> types);
	
	PRIORITY getReporterPriority();
	void setReporterPriority(PRIORITY reporterPriority);
	
	PRIORITY getSupporterPriority();
	void setSupporterPriority(PRIORITY supporterPriority);
}