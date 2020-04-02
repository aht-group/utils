package org.jeesl.web.mbean.prototype.module.hd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
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
								FGA extends JeeslHdFga<FAQ,DOC,SEC>,
								DOC extends JeeslIoCms<L,D,?,SEC,LOC>,
								SEC extends JeeslIoCmsSection<L,SEC>,
								USER extends JeeslSimpleUser
								>
					extends AbstractHelpdeskBean<L,D,LOC,R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER>
					implements Serializable//,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHdFaqBean.class);
	
	private final IoCmsFactoryBuilder<L,D,LOC,?,DOC,?,SEC,?,?,?,?,?,?,?> fbCms;
	
	private JeeslIoCmsFacade<L,D,LOC,?,DOC,?,SEC,?,?,?,?,?,?,?> fCms;
	
	protected final SbSingleHandler<DOC> sbhDocuments; public SbSingleHandler<DOC> getSbhDocuments() {return sbhDocuments;}
	
	private final List<FAQ> faqs; public List<FAQ> getFaqs() {return faqs;}
	private final List<FGA> answers; public List<FGA> getAnswers() {return answers;}
	private final List<SEC> sections; public List<SEC> getSections() {return sections;}
	
	private FAQ faq; public FAQ getFaq() {return faq;} public void setFaq(FAQ faq) {this.faq = faq;}
	private FGA fga; public FGA getFga() {return fga;} public void setFga(FGA fga) {this.fga = fga;}
	
	public AbstractHdFaqBean(HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER> fbHd,
								IoCmsFactoryBuilder<L,D,LOC,?,DOC,?,SEC,?,?,?,?,?,?,?> fbCms)
	{
		super(fbHd);
		this.fbCms=fbCms;
		
		sbhDocuments = new SbSingleHandler<DOC>(fbHd.getClassDoc(),null);
		
		faqs = new ArrayList<>();
		answers = new ArrayList<>();
		sections = new ArrayList<>();
	}

	protected void postConstructHdFaq(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER> fHd,
									JeeslIoCmsFacade<L,D,LOC,?,DOC,?,SEC,?,?,?,?,?,?,?> fCms,
									R realm)
	{
		super.postConstructHd(bTranslation,bMessage,fHd,realm);
		this.fCms=fCms;
	}
	
	public void selectedTicket() {} // Dummy Implementation
	
	@Override protected void updatedRealmReference()
	{
		reloadFaqs();
		try {reloadDocuments();}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}
	protected abstract void reloadDocuments() throws JeeslNotFoundException;
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{

	}
	
	private void reset(boolean rFaq, boolean rAnswer, boolean rSections)
	{
		if(rFaq) {faq=null;}
		if(rAnswer) {fga=null;}
		if(rSections) {sections.clear();}
	}
	
	private void reloadFaqs()
	{	
		faqs.clear();
		faqs.addAll(fHd.all(fbHd.getClassFaq(),realm,rref));
	}
	
	public void selectFaq()
	{
		reset(false,true,true);
		logger.info(AbstractLogMessage.selectEntity(faq));
		faq = efLang.persistMissingLangs(fHd,bTranslation.getLocales(),faq);
		faq = efDescription.persistMissingLangs(fHd,bTranslation.getLocales(),faq);
		reloadAnswers();
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
		reloadAnswers();
	}
	
	public void deleteFaq() throws JeeslConstraintViolationException, JeeslLockingException
	{
		fHd.rm(faq);
		reset(true,true,true);
		reloadFaqs();
	}
	
	private void reloadAnswers()
	{
		answers.clear();
		answers.addAll(fHd.allForParent(fbHd.getClassFga(), faq));
	}
	
	
	public void selectAnswer()
	{
		logger.info(AbstractLogMessage.selectEntity(fga));
		reset(false,false,true);
		reloadSections();
	}
	
	public void addAnswer()
	{
		logger.info(AbstractLogMessage.addEntity(fbHd.getClassFga()));
		reset(false,true,true);
		if(sbhDocuments.getHasSome())
		{
			fga = fbHd.ejbFga().build(faq,sbhDocuments.getList().get(0),answers);
			reloadSections();
		}
	}
	
	public void onDocumentChanged()
	{
		reset(false,false,true);
		fbHd.ejbFga().converter(fHd,fga);
		reloadSections();
	}
	
	public void reloadSections()
	{
		SEC root = fCms.load(fga.getDocument().getRoot(),true);
		sections.addAll(fbCms.ejbSection().toSections(root));
	}
	
	public void saveAnswer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(fga));
		fbHd.ejbFga().converter(fHd,fga);
		fga = fHd.save(fga);
		reloadAnswers();
	}
	
	public void deleteAnswer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(fga));
		fHd.rm(fga);
		reset(false,true,true);
		reloadAnswers();
	}
}