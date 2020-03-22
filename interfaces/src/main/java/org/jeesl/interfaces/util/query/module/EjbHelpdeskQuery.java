package org.jeesl.interfaces.util.query.module;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdResolutionLevel;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.util.query.AbstractEjbQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHelpdeskQuery<L extends JeeslLang,D extends JeeslDescription,
							R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
							TICKET extends JeeslHdTicket<R,EVENT,?>,
							CAT extends JeeslHdTicketCategory<L,D,R,CAT,?>,
							STATUS extends JeeslHdTicketStatus<L,D,R,STATUS,?>,
							EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,USER>,
							TYPE extends JeeslHdEventType<L,D,TYPE,?>,
							LEVEL extends JeeslHdResolutionLevel<L,D,R,LEVEL,?>,
							USER extends JeeslSimpleUser>
			extends AbstractEjbQuery
{
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = LoggerFactory.getLogger(EjbHelpdeskQuery.class);
	

	private EjbHelpdeskQuery()
	{       

	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
					TICKET extends JeeslHdTicket<R,EVENT,?>,
					CAT extends JeeslHdTicketCategory<L,D,R,CAT,?>,
					STATUS extends JeeslHdTicketStatus<L,D,R,STATUS,?>,
					EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,USER>,
					TYPE extends JeeslHdEventType<L,D,TYPE,?>,
					LEVEL extends JeeslHdResolutionLevel<L,D,R,LEVEL,?>,
					USER extends JeeslSimpleUser>
			EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,USER>
			build() {return new EjbHelpdeskQuery<>();}

	@Override
	public void reset()
	{
		realms=null;
		reporters=null;
	}
	
	private List<R> realms; public List<R> getRealm() {return realms;}
	public EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,USER> add(R realm) {if(realms==null) {realms = new ArrayList<R>();} realms.add(realm); return this;}

	private List<USER> reporters; public List<USER> getReporters() {return reporters;}
	public EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,USER> addReporter(USER user) {if(reporters==null) {reporters = new ArrayList<USER>();} reporters.add(user); return this;} 
}