package org.jeesl.interfaces.model.module.feedback;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFeedbackThread<L extends JeeslLang, D extends JeeslDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends JeeslStatus<STYLE,L,D>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
						extends EjbWithId
{	
	List<FEEDBACK> getFeedbacks();
	void setFeedbacks(List<FEEDBACK> feedbacks);
}