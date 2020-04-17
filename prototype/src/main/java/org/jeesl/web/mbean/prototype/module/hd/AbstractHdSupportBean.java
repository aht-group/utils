package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.controller.handler.ui.UiEditHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFga;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdMessage;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdScope;
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
import org.openfuxml.content.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.xml.JaxbUtil;

public abstract class AbstractHdSupportBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
								TICKET extends JeeslHdTicket<R,EVENT,M,FRC>,
								CAT extends JeeslHdTicketCategory<L,D,R,CAT,?>,
								STATUS extends JeeslHdTicketStatus<L,D,R,STATUS,?>,
								EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,PRIORITY,USER>,
								TYPE extends JeeslHdEventType<L,D,TYPE,?>,
								LEVEL extends JeeslHdLevel<L,D,R,LEVEL,?>,
								PRIORITY extends JeeslHdPriority<L,D,R,PRIORITY,?>,
								MSG extends JeeslHdMessage<TICKET,M,SCOPE,USER>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								FAQ extends JeeslHdFaq<L,D,R,CAT,SCOPE>,
								SCOPE extends JeeslHdScope<L,D,SCOPE,?>,
								FGA extends JeeslHdFga<FAQ,DOC,SEC>,
								DOC extends JeeslIoCms<L,D,?,SEC,LOC>,
								SEC extends JeeslIoCmsSection<L,SEC>,
								FRC extends JeeslFileContainer<?,?>,
								USER extends JeeslSimpleUser
								>
					extends AbstractHelpdeskBean<L,D,LOC,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,FRC,USER>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdSupportBean.class);

	
	private final UiEditHandler<MSG> editHandler; public UiEditHandler<MSG> getEditHandler() {return editHandler;}
	
	private final List<EVENT> events;  public List<EVENT> getEvents() {return events;}
	private final List<MSG> messages; public List<MSG> getMessages() {return messages;}
	private final List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	protected final List<USER> supporters;  public List<USER> getSupporters() {return supporters;}
	
	private USER supporter;
	private MSG message; public MSG getMessage() {return message;} public void setMessage(MSG message) {this.message = message;}
	protected Section ofxMessage; public Section getOfxMessage() {return ofxMessage;}
	
	public AbstractHdSupportBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,FRC,USER> fbHd)
	{
		super(fbHd);
		
		editHandler = new UiEditHandler<>();
		
		events = new ArrayList<>();
		messages = new ArrayList<>();
		scopes = new ArrayList<>();
		supporters = new ArrayList<>();
	}

	protected void postConstructHdSupport(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER> fHd,
									R realm,
									USER supporter)
	{
		super.postConstructHd(bTranslation,bMessage,fHd,realm);
		scopes.addAll(fHd.allOrderedPositionVisible(fbHd.getClassScope()));
		this.supporter=supporter;
	}
	
	@Override protected void updatedRealmReference()
	{
		preSelectSbh();
		reloadSupporters();
		reloadTickets();
	}
	protected abstract void preSelectSbh();
	protected abstract void reloadSupporters();
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		reloadTickets();
	}
	
	private void reloadTickets()
	{
		EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,USER> query = EjbHelpdeskQuery.build();
		if(sbhStatus.hasSelected()) {query.addStatus(sbhStatus.getSelected());} else {query.nullStatus();}
		
		tickets.clear();
		tickets.addAll(fHd.fHdTickets(query));
	}
	
	public void selectedTicket()
	{
		reloadEvents();
		reloadMessages();
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
		ticket = fHd.saveHdTicket(ticket,lastEvent,supporter);
		lastEvent = ticket.getLastEvent();
		reloadTickets();
		reloadEvents();
	}
	
	private void reloadMessages()
	{
		messages.clear();
		messages.addAll(fHd.allForParent(fbHd.getClassMessage(),ticket));
		logger.info(AbstractLogMessage.reloaded(fbHd.getClassMessage(),messages));
	}
	
	public void selectMessage()
	{
		logger.info(AbstractLogMessage.selectEntity(message));
		editHandler.update(message);
		ofxMessage = ofxMarkup.build(message.getMarkup());
		JaxbUtil.info(ofxMessage);
	}
	
	public void addMessage()
	{
		logger.info(AbstractLogMessage.addEntity(fbHd.getClassMessage()));
		MT type = fHd.fByEnum(fbHd.getClassMarkupType(),JeeslIoCmsMarkupType.Code.xhtml);
		SCOPE scope = fHd.fByEnum(fbHd.getClassScope(),JeeslHdScope.Code.suppport);
		message = fbHd.ejbMessage().build(ticket,type,scope,supporter);
		editHandler.update(message);
		logger.info(message.getScope().toString());
	}
	
	public void saveMessage() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.savedEntity(message));
		fbHd.ejbMessage().converter(fHd,message);
		message = fHd.save(message);
		editHandler.saved(message);
		reloadMessages();
	}
}