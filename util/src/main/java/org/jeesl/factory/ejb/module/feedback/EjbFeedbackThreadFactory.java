package org.jeesl.factory.ejb.module.feedback;

import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbFeedbackThreadFactory<L extends JeeslLang, D extends JeeslDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends JeeslStatus<STYLE,L,D>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackThreadFactory.class);
	
	private final Class<THREAD> cThread;
	
    public EjbFeedbackThreadFactory(final Class<THREAD> cThread)
    {
        this.cThread = cThread;
    } 
	
	public THREAD build()
	{
		try
		{
			THREAD ejb = cThread.newInstance();

		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}