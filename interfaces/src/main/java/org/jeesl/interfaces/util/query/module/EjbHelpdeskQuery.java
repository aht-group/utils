package org.jeesl.interfaces.util.query.module;

import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.util.query.AbstractEjbQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHelpdeskQuery<R extends JeeslMcsRealm<?,?,R,?>, RREF extends EjbWithId,
							TICKET extends JeeslHdTicket<R,EVENT,M>,
							CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
							STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
							EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,USER>,
							TYPE extends JeeslHdEventType<?,?,TYPE,?>,
							M extends JeeslMarkup<MT>,
							MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
							USER extends JeeslSimpleUser>
			extends AbstractEjbQuery
{
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = LoggerFactory.getLogger(EjbHelpdeskQuery.class);
	

	private EjbHelpdeskQuery()
	{       

	}
	
	public static <R extends JeeslMcsRealm<?,?,R,?>, RREF extends EjbWithId,
					TICKET extends JeeslHdTicket<R,EVENT,M>,
					CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
					STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
					EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,USER>,
					TYPE extends JeeslHdEventType<?,?,TYPE,?>,
					M extends JeeslMarkup<MT>,
					MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
					USER extends JeeslSimpleUser>
			EjbHelpdeskQuery<R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,M,MT,USER>
			build() {return new EjbHelpdeskQuery<>();}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}
}