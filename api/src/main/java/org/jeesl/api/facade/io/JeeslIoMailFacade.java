package org.jeesl.api.facade.io;

import java.util.Date;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.io.mail.core.JeeslMailRetention;
import org.jeesl.interfaces.model.io.mail.core.JeeslMailStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.jeesl.model.xml.system.io.mail.Mail;

public interface JeeslIoMailFacade <L extends JeeslLang,D extends JeeslDescription,
									CATEGORY extends JeeslStatus<L,D,CATEGORY>,
									MAIL extends JeeslIoMail<L,D,CATEGORY,STATUS,RETENTION,FRC>,
									STATUS extends JeeslMailStatus<L,D,STATUS,?>,
									RETENTION extends JeeslMailRetention<L,D,RETENTION,?>,
									FRC extends JeeslFileContainer<?,?>>
			extends JeeslFacade
{	
	Integer cQueue();
	List<MAIL> fMails(List<CATEGORY> categories, List<STATUS> status, List<RETENTION> retentions, Date from, Date to, Integer maxResult);
	List<MAIL> fSpoolMails(int max);
	
	void queueMail(CATEGORY category, RETENTION retention, Mail mail) throws JeeslConstraintViolationException;
	
	Json1Tuples<STATUS> tpcIoMailByStatus(Date from, Date to, List<CATEGORY> categories);
}