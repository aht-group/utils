package org.jeesl.interfaces.model.module.feedback;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFeedback<L extends JeeslLang, D extends JeeslDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends JeeslStatus<STYLE,L,D>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
						extends EjbWithId,
								EjbSaveable,
								EjbWithRecord
{	
	THREAD getThread();
	void setThread(THREAD thread);
	
	STYLE getStyle();
	void setStyle(STYLE style);
	
	TYPE getType();
	void setType(TYPE type);
	
	USER getUser();
	void setUser(USER user);
	
	String getTxt();
	void setTxt(String txt);
}