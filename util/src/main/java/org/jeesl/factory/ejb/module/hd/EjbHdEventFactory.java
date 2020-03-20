package org.jeesl.factory.ejb.module.hd;

import java.util.Date;

import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHdEventFactory<TICKET extends JeeslHdTicket<?,EVENT>,
								CAT extends JeeslHdTicketCategory<?,?,?,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,?,STATUS,?>,
								EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,USER>,
								TYPE extends JeeslHdEventType<?,?,TYPE,?>,
								USER extends JeeslSimpleUser>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHdEventFactory.class);
	
	private final Class<EVENT> cEvent;
	
    public EjbHdEventFactory(final Class<EVENT> cEvent)
    {
        this.cEvent = cEvent;
    }
	
	public EVENT build(TICKET ticket, CAT category, STATUS status, USER reporter)
	{
		try
		{
			EVENT ejb = cEvent.newInstance();
			ejb.setTicket(ticket);
			ejb.setCategory(category);
			ejb.setStatus(status);
			ejb.setRecord(new Date());
			ejb.setReporter(reporter);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}