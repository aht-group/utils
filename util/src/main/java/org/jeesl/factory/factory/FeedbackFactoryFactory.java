package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.module.feedback.EjbFeedbackFactory;
import org.jeesl.factory.ejb.module.feedback.EjbFeedbackThreadFactory;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class FeedbackFactoryFactory<L extends UtilsLang, D extends UtilsDescription,
									THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
									FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
									STYLE extends UtilsStatus<STYLE,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(FeedbackFactoryFactory.class);
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<THREAD> cThread;
	private final Class<FEEDBACK> cFeedback;
    
	private FeedbackFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<THREAD> cThread, final Class<FEEDBACK> cFeedback)
	{       
		this.cL = cL;
		this.cD = cD;
		this.cThread = cThread;
        this.cFeedback = cFeedback;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
					FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					USER extends EjbWithEmail>
		FeedbackFactoryFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> factory(final Class<L> cL,final Class<D> cD,final Class<THREAD> cThread, final Class<FEEDBACK> cFeedback)
	{
		return new FeedbackFactoryFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>(cL,cD,cThread,cFeedback);
	}
	
	public EjbFeedbackThreadFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> thread()
	{
		return new EjbFeedbackThreadFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>(cThread);
	}
	
	public EjbFeedbackFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> feedback()
	{
		return new EjbFeedbackFactory<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>(cFeedback);
	}

}