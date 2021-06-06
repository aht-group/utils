package org.jeesl.interfaces.model.module.feedback;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithEmail;

public interface JeeslFeedbackThread<L extends JeeslLang, D extends JeeslDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends JeeslStatus<L,D,STYLE>,
								TYPE extends JeeslStatus<L,D,TYPE>,
								USER extends EjbWithEmail>
						extends EjbWithId
{	
	List<FEEDBACK> getFeedbacks();
	void setFeedbacks(List<FEEDBACK> feedbacks);
}