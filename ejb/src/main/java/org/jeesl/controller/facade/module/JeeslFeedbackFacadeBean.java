package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslFeedbackFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;

public class JeeslFeedbackFacadeBean<L extends JeeslLang, D extends JeeslDescription,
										THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
										FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
										STYLE extends JeeslStatus<STYLE,L,D>,
										TYPE extends JeeslStatus<TYPE,L,D>,
										USER extends EjbWithEmail>
					extends JeeslFacadeBean
					implements JeeslFeedbackFacade<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>
{	

	private final Class<THREAD> cThread;
		
	public JeeslFeedbackFacadeBean(EntityManager em, final Class<THREAD> cThread)
	{
		super(em);
		this.cThread=cThread;
	}

	@Override public THREAD load(THREAD thread)
	{
		thread = em.find(cThread, thread.getId());
		thread.getFeedbacks().size();
		return thread;
	}
}