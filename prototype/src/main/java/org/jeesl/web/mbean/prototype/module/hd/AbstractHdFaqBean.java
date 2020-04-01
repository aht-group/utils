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
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractHdFaqBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
								FAQ extends JeeslHdFaq<L,D,R,CAT,SCOPE>,
								SCOPE extends JeeslHdScope<L,D,SCOPE,?>,
								SEC extends JeeslIoCmsSection<L,SEC>,
								USER extends JeeslSimpleUser
								>
					extends AbstractHelpdeskBean<L,D,LOC,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,SEC,USER>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdFaqBean.class);
		
	protected final List<FAQ> faqs; public List<FAQ> getFaqs() {return faqs;}
	
	protected FAQ faq; public FAQ getFaq() {return faq;} public void setFaq(FAQ faq) {this.faq = faq;}

	public AbstractHdFaqBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,USER> fbHd)
	{
		super(fbHd);
		
		faqs = new ArrayList<>();
	}

	protected void postConstructHdFaq(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,USER> fHd,
									R realm)
	{
		super.postConstructHd(bTranslation,bMessage,fHd,realm);
	}
	
	public void selectedTicket() {} // Dummy Implementation
	
	@Override protected void updatedRealmReference()
	{
		reloadFaqs();
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{

	}
	
	private void reset(boolean rFaq)
	{
		if(rFaq) {faq=null;}
	}
	
	private void reloadFaqs()
	{	
		faqs.clear();
		faqs.addAll(fHd.all(fbHd.getClassFaq(),realm,rref));
	}
	
	public void selectFaq()
	{
		logger.info(AbstractLogMessage.selectEntity(faq));
		faq = efLang.persistMissingLangs(fHd,bTranslation.getLocales(),faq);
		faq = efDescription.persistMissingLangs(fHd,bTranslation.getLocales(),faq);
	}
	
	public void addFaq()
	{
		logger.info(AbstractLogMessage.addEntity(fbHd.getClassFaq()));
		faq = fbHd.ejbFaq().build(realm,rref,sbhCategory.getList().get(0),sbhScope.getList().get(0));
		faq.setName(efLang.createEmpty(bTranslation.getLocales()));
		faq.setDescription(efDescription.createEmpty(bTranslation.getLocales()));
	}
	
	public void saveFaq() throws JeeslConstraintViolationException, JeeslLockingException
	{
		fbHd.ejbFaq().converter(fHd,faq);
		faq = fHd.save(faq);
		reloadFaqs();
	}
	
	public void deleteFaq() throws JeeslConstraintViolationException, JeeslLockingException
	{
		fHd.rm(faq);
		reset(true);
		reloadFaqs();
	}
}