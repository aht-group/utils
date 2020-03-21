package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.util.query.module.EjbHelpdeskQuery;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractHdTicketBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
								TICKET extends JeeslHdTicket<R,EVENT,M>,
								CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
								EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,USER>,
								TYPE extends JeeslHdEventType<L,D,TYPE,?>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,

								USER extends JeeslSimpleUser
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdTicketBean.class);
	
	private final HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,M,MT,USER> fbHd;

	private JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,M,MT,USER> fHd;
	
	private final List<TICKET> tickets;  public List<TICKET> getTickets() {return tickets;}
	private final List<CAT> categories; public List<CAT> getCategories() {return categories;}
	private final List<STATUS> statuse; 
	
	private R realm;
	private RREF rref;
	private USER reporter;
	
	private TICKET ticket; public TICKET getTicket() {return ticket;} public void setTicket(TICKET ticket) {this.ticket = ticket;}
	private EVENT firstEvent; public EVENT getFirstEvent() {return firstEvent;} public void setFirstEvent(EVENT firstEvent) {this.firstEvent = firstEvent;}
	private EVENT lastEvent; public EVENT getLastEvent() {return lastEvent;} public void setLastEvent(EVENT lastEvent) {this.lastEvent = lastEvent;}

	public AbstractHdTicketBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,M,MT,USER> fbHd)
	{
		super(fbHd.getClassL(),fbHd.getClassD());
		this.fbHd=fbHd;
		tickets = new ArrayList<>();
		categories = new ArrayList<>();
		statuse = new ArrayList<>();
	}

	protected void postConstructBb(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,M,MT,USER> fHd,
									R realm,
									USER reporter)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fHd=fHd;
		this.realm=realm;
		this.reporter=reporter;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		
		categories.addAll(fHd.all(fbHd.getClassCategory(),realm,rref));
		statuse.addAll(fHd.all(fbHd.getClassTicketStatus(),realm,rref));
		
		reloadTickets();
	}
	
	private void reloadTickets()
	{
		tickets.clear();
		tickets.addAll(fHd.fHdTickets(EjbHelpdeskQuery.build()));
	}
	
	public void selectTicket()
	{
		logger.info(AbstractLogMessage.selectEntity(ticket));
		ticket = fHd.find(fbHd.getClassTicket(),ticket);
		firstEvent = fHd.find(fbHd.getClassEvent(),ticket.getFirstEvent());
		lastEvent = fHd.find(fbHd.getClassEvent(),ticket.getLastEvent());
	}
	
	public void addTicket()
	{
		logger.info(AbstractLogMessage.addEntity(fbHd.getClassTicket()));
		ticket = fbHd.ejbTicket().build(realm,rref);
		firstEvent = fbHd.ejbEvent().build(ticket,categories.get(0),statuse.get(0),reporter);
		lastEvent = fbHd.ejbEvent().build(ticket,categories.get(0),statuse.get(0),reporter);
	}
	
	public void saveTicket() throws JeeslConstraintViolationException, JeeslLockingException
	{
		ticket = fHd.saveHdTicket(ticket,lastEvent);
		reloadTickets();
	}
	
	
}