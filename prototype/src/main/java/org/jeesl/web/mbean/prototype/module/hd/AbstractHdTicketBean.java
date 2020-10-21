package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.controller.handler.ui.UiEditHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
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
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractHdTicketBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
								DOC extends JeeslIoCms<L,D,LOC,?,SEC>,
								SEC extends JeeslIoCmsSection<L,SEC>,
								FRC extends JeeslFileContainer<?,?>,
								USER extends JeeslSimpleUser
								>
					extends AbstractHelpdeskBean<L,D,LOC,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,FRC,USER>
					implements Serializable,JeeslFileRepositoryCallback//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdTicketBean.class);
	
	private final UiEditHandler<TICKET> editHandler; public UiEditHandler<TICKET> getEditHandler() {return editHandler;}
	
	private USER reporter;
	
	public AbstractHdTicketBean(HdFactoryBuilder<L,D,LOC,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,FRC,USER> fbHd)
	{
		super(fbHd);
		
		editHandler = new UiEditHandler<>();
	}

	protected void postConstructHdTicket(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,LOC,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER> fHd,
									R realm,
									USER reporter)
	{
		super.postConstructHd(bTranslation,bMessage,fHd,realm);

		this.reporter=reporter;
	}
	
	@Override protected void updatedRealmReference()
	{		
		reloadTickets();
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		// TODO Auto-generated method stub
	}
	
	private void reloadTickets()
	{
		EjbHelpdeskQuery<L,D,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,USER> query = EjbHelpdeskQuery.build();
		query.addReporter(reporter);
		
		tickets.clear();
		tickets.addAll(fHd.fHdTickets(query));
	}
	
	public void selectedTicket()
	{
		editHandler.update(ticket);
	}
	
	public void addTicket() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.addEntity(fbHd.getClassTicket()));
		MT type = fHd.fByEnum(fbHd.getClassMarkupType(),JeeslIoCmsMarkupType.Code.xhtml);
		ticket = fbHd.ejbTicket().build(realm,rref,type);
		PRIORITY priority = getDefaultPriority();
		firstEvent = fbHd.ejbEvent().build(ticket,sbhCategory.getList().get(0),sbhStatus.getList().get(0),levels.get(0),priority,reporter);
		lastEvent = fbHd.ejbEvent().build(ticket,sbhCategory.getList().get(0),sbhStatus.getList().get(0),levels.get(0),priority,reporter);
		editHandler.update(ticket);
		ofxUser = XmlSectionFactory.build();
		if(frh!=null) {frh.init(ticket);}
	}
	private PRIORITY getDefaultPriority()
	{
		for(PRIORITY p : priorities)
		{
			if(p.getCode().equals("medium")) {return p;}
		}
		return priorities.get(0);
	}
	
	public void saveTicket() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		fbHd.ejbEvent().converter(fHd,lastEvent);
		if(EjbIdFactory.isUnSaved(ticket))
		{
			ticket = fHd.saveHdTicket(ticket,lastEvent,reporter);
			callBackNewTicket(ticket);
		}
		else {ticket = fHd.save(ticket);}
		editHandler.saved(ticket);
		ofxUser = ofxMarkup.build(ticket.getMarkupUser());
		if(frh!=null)
		{
			frh.saveDeferred(ticket);
			ticket.setFrContainer(frh.getContainer());
			ticket = fHd.save(ticket);
		}
		reloadTickets();
	}
	
	protected abstract void callBackNewTicket(TICKET ticket);
	
	@Override public void callbackFrContainerSaved(EjbWithId id) throws JeeslConstraintViolationException, JeeslLockingException{}
	@Override public void callbackFrMetaSelected() {}
}