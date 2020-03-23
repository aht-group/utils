package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.hd.EjbHdEventFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdTicketFactory;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HdFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
							R extends JeeslMcsRealm<L,D,R,?>,
							TICKET extends JeeslHdTicket<R,EVENT,M>,
							CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
							STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
							EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,PRIORITY,USER>,
							TYPE extends JeeslHdEventType<L,D,TYPE,?>,
							LEVEL extends JeeslHdLevel<L,D,R,LEVEL,?>,
							PRIORITY extends JeeslHdPriority<L,D,R,PRIORITY,?>,
							M extends JeeslMarkup<MT>,
							MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
							USER extends JeeslSimpleUser>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(HdFactoryBuilder.class);
	
	private final Class<TICKET> cTicket; public Class<TICKET> getClassTicket() {return cTicket;}
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<STATUS> cTicketStatus; public Class<STATUS> getClassTicketStatus() {return cTicketStatus;}
	
	private final Class<EVENT> cEvent; public Class<EVENT> getClassEvent() {return cEvent;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}
	private final Class<LEVEL> cLevel; public Class<LEVEL> getClassLevel() {return cLevel;}
	
	private final Class<M> cMarkup; public Class<M> getClassMarkup() {return cMarkup;}
	private final Class<MT> cMarkupType; public Class<MT> getClassMarkupType() {return cMarkupType;}
	
	private final Class<USER> cUser; public Class<USER> getClassUser() {return cUser;}

	public HdFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<TICKET> cTicket,
								final Class<CAT> cCategory,
								final Class<STATUS> cTicketStatus,
								final Class<EVENT> cEvent,
								final Class<TYPE> cType,
								final Class<LEVEL> cLevel,
								final Class<M> cMarkup,
								final Class<MT> cMarkupType,
								final Class<USER> cUser)
	{       
		super(cL,cD);
		this.cTicket=cTicket;
		this.cCategory=cCategory;
		this.cTicketStatus=cTicketStatus;
		this.cEvent=cEvent;
		this.cType=cType;
		this.cLevel=cLevel;
		this.cMarkup=cMarkup;
		this.cMarkupType=cMarkupType;
		this.cUser=cUser;
	}

	public EjbHdTicketFactory<R,TICKET,M,MT> ejbTicket() {return new EjbHdTicketFactory<>(cTicket,cMarkup);}
	public EjbHdEventFactory<TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,USER> ejbEvent() {return new EjbHdEventFactory<>(this);}
}