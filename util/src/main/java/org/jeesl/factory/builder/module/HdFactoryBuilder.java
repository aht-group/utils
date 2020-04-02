package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.hd.EjbHdEventFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdFaqFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdFgaFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdTicketFactory;
import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFga;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdScope;
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
							CAT extends JeeslHdTicketCategory<L,D,R,CAT,?>,
							STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
							EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,PRIORITY,USER>,
							TYPE extends JeeslHdEventType<L,D,TYPE,?>,
							LEVEL extends JeeslHdLevel<L,D,R,LEVEL,?>,
							PRIORITY extends JeeslHdPriority<L,D,R,PRIORITY,?>,
							M extends JeeslMarkup<MT>,
							MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
							FAQ extends JeeslHdFaq<L,D,R,CAT,SCOPE>,
							SCOPE extends JeeslHdScope<L,D,SCOPE,?>,
							FGA extends JeeslHdFga<FAQ,DOC,SEC>,
							DOC extends JeeslIoCms<L,D,?,SEC,?>,
							SEC extends JeeslIoCmsSection<L,SEC>,
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
	private final Class<PRIORITY> cPriority; public Class<PRIORITY> getClassPriority() {return cPriority;}
	
	private final Class<M> cMarkup; public Class<M> getClassMarkup() {return cMarkup;}
	private final Class<MT> cMarkupType; public Class<MT> getClassMarkupType() {return cMarkupType;}
	private final Class<FAQ> cFaq; public Class<FAQ> getClassFaq() {return cFaq;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<FGA> cFga; public Class<FGA> getClassFga() {return cFga;}
	private final Class<SEC> cSection; public Class<SEC> getClassSection() {return cSection;}
	
	private final Class<USER> cUser; public Class<USER> getClassUser() {return cUser;}

	public HdFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<TICKET> cTicket,
								final Class<CAT> cCategory,
								final Class<STATUS> cTicketStatus,
								final Class<EVENT> cEvent,
								final Class<TYPE> cType,
								final Class<LEVEL> cLevel,
								final Class<PRIORITY> cPriority,
								final Class<M> cMarkup,
								final Class<MT> cMarkupType,
								final Class<FAQ> cFaq,
								final Class<SCOPE> cScope,
								final Class<FGA> cFga,
								final Class<SEC> cSection,
								final Class<USER> cUser)
	{       
		super(cL,cD);
		this.cTicket=cTicket;
		this.cCategory=cCategory;
		this.cTicketStatus=cTicketStatus;
		this.cEvent=cEvent;
		this.cType=cType;
		this.cLevel=cLevel;
		this.cPriority=cPriority;
		this.cMarkup=cMarkup;
		this.cMarkupType=cMarkupType;
		this.cFaq=cFaq;
		this.cScope=cScope;
		this.cFga=cFga;
		this.cSection=cSection;
		this.cUser=cUser;
	}

	public EjbHdTicketFactory<R,TICKET,M,MT> ejbTicket() {return new EjbHdTicketFactory<>(cTicket,cMarkup);}
	public EjbHdEventFactory<TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,USER> ejbEvent() {return new EjbHdEventFactory<>(this);}
	public EjbHdFaqFactory<R,CAT,FAQ,SCOPE> ejbFaq() {return new EjbHdFaqFactory<>(this);}
	public EjbHdFgaFactory<FAQ,FGA,DOC,SEC> ejbFga() {return new EjbHdFgaFactory<>(this);}
}