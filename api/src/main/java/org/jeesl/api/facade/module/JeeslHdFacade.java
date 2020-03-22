package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdResolutionLevel;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.util.query.module.EjbHelpdeskQuery;

public interface JeeslHdFacade <L extends JeeslLang,D extends JeeslDescription,
								R extends JeeslMcsRealm<L,D,R,?>, 
								TICKET extends JeeslHdTicket<R,EVENT,M>,
								CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
								EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,USER>,
								TYPE extends JeeslHdEventType<L,D,TYPE,?>,
								LEVEL extends JeeslHdResolutionLevel<L,D,R,LEVEL,?>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>,

								USER extends JeeslSimpleUser>
			extends JeeslFacade
{	
	TICKET saveHdTicket(TICKET ticket, EVENT event) throws JeeslConstraintViolationException, JeeslLockingException;
	
	<RREF extends EjbWithId> List<TICKET> fHdTickets(EjbHelpdeskQuery<R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,USER> query);
}