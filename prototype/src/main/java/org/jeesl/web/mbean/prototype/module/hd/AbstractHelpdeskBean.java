package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
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
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractHelpdeskBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
								FAQ extends JeeslHdFaq<L,D,R,CAT>,
								USER extends JeeslSimpleUser
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHelpdeskBean.class);
	
	protected final HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,USER> fbHd;

	protected JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,USER> fHd;
	
	protected final SbMultiHandler<STATUS> sbhStatus; public SbMultiHandler<STATUS> getSbhStatus() {return sbhStatus;}
	
	protected final List<CAT> categories; public List<CAT> getCategories() {return categories;}
	protected final List<LEVEL> levels; public List<LEVEL> getLevels() {return levels;}
	protected final List<PRIORITY> priorities; public List<PRIORITY> getPriorities() {return priorities;}
	
	protected final List<TICKET> tickets;  public List<TICKET> getTickets() {return tickets;}
	
	protected R realm;
	protected RREF rref;
	protected TICKET ticket; public TICKET getTicket() {return ticket;} public void setTicket(TICKET ticket) {this.ticket = ticket;}
	protected EVENT firstEvent; public EVENT getFirstEvent() {return firstEvent;} public void setFirstEvent(EVENT firstEvent) {this.firstEvent = firstEvent;}
	protected EVENT lastEvent; public EVENT getLastEvent() {return lastEvent;} public void setLastEvent(EVENT lastEvent) {this.lastEvent = lastEvent;}

	
	public AbstractHelpdeskBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,USER> fbHd)
	{
		super(fbHd.getClassL(),fbHd.getClassD());
		this.fbHd=fbHd;
		
		sbhStatus = new SbMultiHandler<>(fbHd.getClassTicketStatus(),this);
		
		categories = new ArrayList<>();
		levels = new ArrayList<>();
		priorities = new ArrayList<>();
		
		tickets = new ArrayList<>();
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,USER> fHd,
									R realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fHd=fHd;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		
		categories.addAll(fHd.all(fbHd.getClassCategory(),realm,rref));
		levels.addAll(fHd.all(fbHd.getClassLevel(),realm,rref));
		priorities.addAll(fHd.all(fbHd.getClassPriority(),realm,rref));
		
		sbhStatus.setList(fHd.all(fbHd.getClassTicketStatus(),realm,rref));
		
		updatedRealmReference();
		
		
	}
	protected abstract void updatedRealmReference();
	
	public void selectTicket()
	{
		logger.info(AbstractLogMessage.selectEntity(ticket));
		ticket = fHd.find(fbHd.getClassTicket(),ticket);
		firstEvent = fHd.find(fbHd.getClassEvent(),ticket.getFirstEvent());
		lastEvent = fHd.find(fbHd.getClassEvent(),ticket.getLastEvent());
		selectedTicket();
	}
	protected abstract void selectedTicket();
}