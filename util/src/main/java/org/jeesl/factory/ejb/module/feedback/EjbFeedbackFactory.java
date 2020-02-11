package org.jeesl.factory.ejb.module.feedback;

import java.util.Date;

import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbFeedbackFactory<L extends JeeslLang, D extends JeeslDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends JeeslStatus<STYLE,L,D>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(EjbFeedbackFactory.class);
	
	private final Class<FEEDBACK> cFeedback;
	
    public EjbFeedbackFactory(final Class<FEEDBACK> cFeedback)
    {
        this.cFeedback = cFeedback;
    }
	
	public FEEDBACK build(THREAD thread, STYLE style, TYPE type, USER user)
	{
		try
		{
			FEEDBACK ejb = cFeedback.newInstance();
			ejb.setThread(thread);
			ejb.setStyle(style);
			ejb.setType(type);
		    ejb.setUser(user);
		    ejb.setRecord(new Date());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}