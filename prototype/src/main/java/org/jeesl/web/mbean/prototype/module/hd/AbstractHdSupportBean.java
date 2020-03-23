package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractHdSupportBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
								TICKET extends JeeslHdTicket<R,EVENT,M>,
								CAT extends JeeslHdTicketCategory<L,D,R,CAT,?>,
								STATUS extends JeeslHdTicketStatus<L,D,R,STATUS,?>,
								EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,PRIORITY,USER>,
								TYPE extends JeeslHdEventType<L,D,TYPE,?>,
								LEVEL extends JeeslHdLevel<L,D,R,LEVEL,?>,
								PRIORITY extends JeeslHdPriority<L,D,R,PRIORITY,?>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								USER extends JeeslSimpleUser
								>
					extends AbstractHelpdeskBean<L,D,LOC,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,USER>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdSupportBean.class);
	
	protected final List<EVENT> events;  public List<EVENT> getEvents() {return events;}
	
	protected final List<USER> supporters;  public List<USER> getSupporters() {return supporters;}
	
	private USER supporter;
	
	public AbstractHdSupportBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,USER> fbHd)
	{
		super(fbHd);
		
		events = new ArrayList<>();
		supporters = new ArrayList<>();
	}

	protected void postConstructHdSupport(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,USER> fHd,
									R realm,
									USER supporter)
	{
		super.postConstructHd(bTranslation,bMessage,fHd,realm);

		this.supporter=supporter;
	}
	
	@Override protected void updatedRealmReference()
	{
		categories.addAll(fHd.all(fbHd.getClassCategory(),realm,rref));
		statuse.addAll(fHd.all(fbHd.getClassTicketStatus(),realm,rref));
		levels.addAll(fHd.all(fbHd.getClassLevel(),realm,rref));
		
		reloadSupporters();
		reloadTickets();
	}
	protected abstract void reloadSupporters();
	
	private void reloadTickets()
	{
		EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,USER> query = EjbHelpdeskQuery.build();
		
		tickets.clear();
		tickets.addAll(fHd.fHdTickets(query));
	}
	
	public void selectedTicket()
	{
		reloadEvents();
	}
	
	private void reloadEvents()
	{
		events.clear();
		events.addAll(fHd.allForParent(fbHd.getClassEvent(),ticket));
		Collections.reverse(events);
		logger.info(AbstractLogMessage.reloaded(fbHd.getClassEvent(),events));
	}
	
	public void saveTicket() throws JeeslConstraintViolationException, JeeslLockingException
	{
		fbHd.ejbEvent().converter(fHd,lastEvent);
		ticket = fHd.saveHdTicket(ticket,lastEvent);
		lastEvent = ticket.getLastEvent();
		reloadTickets();
		reloadEvents();
	}
}