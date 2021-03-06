package org.jeesl.api.facade.system;

import java.util.Date;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.job.EjbWithMigrationJob1;
import org.jeesl.interfaces.model.system.job.EjbWithMigrationJob2;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobCategory;
import org.jeesl.interfaces.model.system.job.JeeslJobExpiration;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedbackType;
import org.jeesl.interfaces.model.system.job.JeeslJobPriority;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobStatus;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.interfaces.model.system.job.JeeslJobType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithEmail;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

public interface JeeslJobFacade <L extends JeeslLang,D extends JeeslDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY,EXPIRE>,
								CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
								TYPE extends JeeslJobType<L,D,TYPE,?>,
								EXPIRE extends JeeslJobExpiration<L,D,EXPIRE,?>,
								JOB extends JeeslJob<TEMPLATE,PRIORITY,FEEDBACK,STATUS,USER>,
								PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>,
								FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,
								FT extends JeeslJobFeedbackType<L,D,FT,?>,
								STATUS extends JeeslJobStatus<L,D,STATUS,?>,
								ROBOT extends JeeslJobRobot<L,D>,
								CACHE extends JeeslJobCache<TEMPLATE,CONTAINER>,
								CONTAINER extends JeeslFileContainer<?,?>,
								USER extends EjbWithEmail
								>
			extends JeeslFacade
{	
	<E extends Enum<E>> TEMPLATE fJobTemplate(E type, String code) throws JeeslNotFoundException;
	List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> type, List<STATUS> status, Date from, Date to);
	List<JOB> fJobs(TEMPLATE template, String code);
	
	JOB fActiveJob(TEMPLATE template, String code) throws JeeslNotFoundException;
	CACHE fJobCache(TEMPLATE template, String code) throws JeeslNotFoundException;
	CACHE uJobCache(TEMPLATE template, String code, byte[] data) throws JeeslConstraintViolationException, JeeslLockingException;
	JOB cJob(USER user, List<FEEDBACK> feedbacks, TEMPLATE template, String code, String name, String jsonFilter) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException;
	
	<T extends EjbWithMigrationJob1<STATUS>> List<T> fEntitiesWithPendingJob1(Class<T> c, int maxResult, boolean includeNull);
	<T extends EjbWithMigrationJob2<STATUS>> List<T> fEntitiesWithPendingJob2(Class<T> c, int maxResult, boolean includeNull);
	<T extends EjbWithMigrationJob1<STATUS>> Json1Tuples<STATUS> tpcJob1Status(Class<T> c);
	<T extends EjbWithMigrationJob2<STATUS>> Json1Tuples<STATUS> tpcJob2Status(Class<T> c);
}